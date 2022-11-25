package com.example.myfyptest.menuCreator.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.myfyptest.R
import com.example.myfyptest.databinding.AddEditDialogComponentBinding
import com.example.myfyptest.databinding.AddNewItemDialogFragmentBinding
import com.example.myfyptest.model.AddMenuViewModel
import java.util.TreeSet

const val TAG = "MenuDialogFragment"

class AddNewItemDialogFragment : DialogFragment() {
    private var _binding: AddNewItemDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private var _includeBinding: AddEditDialogComponentBinding? = null
    private val includeBinding get() = _includeBinding!!
    private val viewModel: AddMenuViewModel by viewModels()

    private lateinit var checkedItems : BooleanArray
    private lateinit var modifierList: Array<String>
    private lateinit var checkedItem : TreeSet<Int>
    private val selectedItems = TreeSet<String>()
    private val modifierLayoutChildren = HashMap<Int,String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val includeView: View = inflater.inflate(R.layout.add_edit_dialog_component, container, false)

        _binding = AddNewItemDialogFragmentBinding.inflate(inflater,container,false)
        _includeBinding = binding.addEditComponent

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modifierList = viewModel.getModifierList().toTypedArray()
        checkedItems = BooleanArray(modifierList.size)
        checkedItem = TreeSet<Int>()

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel)
        }
        Log.d(TAG,"view created")
        binding.toolbar.title = "Add new food"
        includeBinding.addModifierButton.setOnClickListener{
            handleAddModifier()
        }
        includeBinding.resetButton.setOnClickListener{
            resetField()
            Toast.makeText(requireContext(),"All field are reset",Toast.LENGTH_SHORT).show()
            Log.d(TAG,"reset button Pressed")
        }
        includeBinding.modifierSwitch.isChecked = false
        modifierLayoutEnabler(false)
        includeBinding.modifierSwitch.setOnClickListener{
            if (includeBinding.modifierSwitch.isChecked){
                modifierLayoutEnabler(true)
            }
            else{
                modifierLayoutEnabler(false)
            }
        }
    }

    private fun handleAddModifier(){
        AlertDialog.Builder(requireContext())
            .setTitle("Select Modifier")
            .setMultiChoiceItems(modifierList,checkedItems
            ) { _, which, isChecked ->
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    checkedItems[which] = true

                    checkedItem.add(which)
                } else if (checkedItem.contains(which)) {
                    // Else, if the item is already in the array, remove it
                    checkedItem.remove(which)
                }
            }
            .setPositiveButton("Done"){
                    _, _ ->
                    selectedItems.clear()
                    for (i in checkedItems.indices) {
                        if (checkedItems[i])
                            selectedItems.add(modifierList[i])
                        //selectedItems contains modifier key
                    }
                    for(i in selectedItems){
                        addModifierRow(i)
                    }
            }
            .setNegativeButton("Cancel"){
                _,_ -> {}
            }
            .create()
            .show()
        Log.d(TAG,"${selectedItems.size}")
    }

    private fun modifierLayoutEnabler(boolean: Boolean){
        includeBinding.addModifierButton.isEnabled  = boolean
        for (i in 0  until includeBinding.modifiersLinearLayout.childCount){
            val child: View = includeBinding.modifiersLinearLayout.getChildAt(i)
            child.findViewById<ImageButton>(R.id.remove_button).isEnabled = boolean
            child.isEnabled = boolean
        }
        includeBinding.modifiersLinearLayout.isEnabled = boolean

    }

    private fun addModifierRow(string: String){
        if (!modifierLayoutChildren.containsValue(string)) {
            val index = includeBinding.modifiersLinearLayout.childCount
            val inflater = layoutInflater.inflate(R.layout.row_add_modifier, null)
            inflater.findViewById<TextView>(R.id.modifier_name_textView).text = viewModel.getModifier(string)?.name
            inflater.findViewById<ImageButton>(R.id.remove_button).setOnClickListener{
                checkedItems[modifierList.indexOf(string)] = false
                modifierLayoutChildren.remove(index)
                includeBinding.modifiersLinearLayout.removeView(inflater)
            }
            includeBinding.modifiersLinearLayout.addView(inflater,index)
            modifierLayoutChildren[index] = string
        }

        Log.d(TAG,"Child count ${includeBinding.modifiersLinearLayout.childCount}")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.action_save) {
            try {
                saveFoodInfo()
                Toast.makeText(requireContext(),"Food successfully added",Toast.LENGTH_LONG).show()
                dismiss()
                return true
            } catch ( e: Exception){
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Failed to create new food due to ${e.message}")
                    .setPositiveButton("Ok"){_,_ -> }
                    .create()
                    .show()
            }
            return false

        } else if (id == android.R.id.home) {
            // handle close button click here
            dismiss()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resetField(){
        includeBinding.productNameEditText.text = null
        includeBinding.productPriceEditText.text = null
        includeBinding.modifierSwitch.isChecked = false
        includeBinding.productDescriptionEditText.text =null
        includeBinding.modifiersLinearLayout.removeAllViews()
        modifierLayoutChildren.clear()
        for(i in checkedItems.indices){
            checkedItems[i] = false
        }
        modifierLayoutEnabler(false)
    }

    private fun saveFoodInfo(){
        viewModel.setName(includeBinding.productNameEditText.text.toString())
        viewModel.setPrice(includeBinding.productPriceEditText.text.toString().toDouble())
        viewModel.setDescription(includeBinding.productDescriptionEditText.text.toString())
        viewModel.setModifiable(includeBinding.modifierSwitch.isChecked)
        if (includeBinding.modifierSwitch.isChecked){
            for (i in checkedItems.indices){
                viewModel.addModifier(modifierList[i])
            }
        }
        viewModel.createFood()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_new_product_dialog,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _includeBinding = null
        _binding = null
    }
}