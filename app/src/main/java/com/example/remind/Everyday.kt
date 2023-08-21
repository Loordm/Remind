package com.example.remind

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class Everyday : AppCompatActivity() {


    var delete: Boolean = false
    var copy: Boolean = false
    var modyfy: Boolean = false
    private var tab : ArrayList<String> = ArrayList()  // table contient الاعمال
    lateinit var customadapter : AdapterClasse
    private lateinit var mysharedpreferences : SharedPreferences
    private lateinit var Checkboxshared : SharedPreferences
    val fileName: String = "everyDay"
    val checkboxfilename: String = "checkBoxFile"

    private lateinit var mytoolbar : Toolbar
    private lateinit var showindication : TextView
    private lateinit var stoppp : TextView
    private lateinit var goback : ImageButton
    private lateinit var pressactionbtn : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var thelistv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showindication = findViewById(R.id.showindication)
        stoppp = findViewById(R.id.stoppp)
        goback = findViewById(R.id.goback)
        pressactionbtn = findViewById(R.id.pressactionbtn)

        /** *************************** Declaration of dinamic listview ************************** **/
        thelistv = findViewById(R.id.thelistv) // liste view a afficher
        var textToShow : String  // inpute de 3amal
        customadapter = AdapterClasse(this , tab , fileName , checkboxfilename)
        customadapter.setIndik(findViewById(R.id.showindication))
        customadapter.setIlghaa(findViewById(R.id.stoppp))
        /** *************************** End Declaration of dinamic listview ********************** **/

        /** ************************************ Show data *********************************** **/
        /** 1) open the file **/
        mysharedpreferences = getSharedPreferences(fileName , 0 ) // 0 = MODE_PRIVATE
        val editor : SharedPreferences.Editor = mysharedpreferences.edit()
        Checkboxshared = getSharedPreferences(checkboxfilename , 0 ) // 0 = MODE_PRIVATE
        val editor2 : SharedPreferences.Editor = Checkboxshared.edit()
        /** fin 1**/
        /** 2) get all data from the file and put it in mymap **/
        val mymap : Map<String , *> = mysharedpreferences.all
        /** fin 2**/
        /** 3) initialise the table and show data **/
        for ( (key , value) in mymap ) {
            tab.add(value.toString())
            customadapter.notifyDataSetChanged()
            thelistv.adapter = customadapter

        }
        /** fin 3**/
        /** ************************************ End Show data ******************************* **/

        // ***************************** Open CheckBox file ********************************

        // ******************************* Fin Open CheckBox file ***************************

        /** ******************************** Tool bar ************************************** **/
        mytoolbar.inflateMenu(R.menu.options)
        mytoolbar.setOnMenuItemClickListener {  menuitem ->
            if(menuitem.itemId == R.id.modify) {
                showindication.text = ""
                stoppp.text = ""
                customadapter.setCopy(false)
                customadapter.setDelete(false)
                modyfy = true
                if (tab.isNotEmpty()) {
                    if (modyfy) {
                        Toast.makeText(this, "إضغط على النص الذي تريد تعديله", Toast.LENGTH_SHORT).show()
                        customadapter.setModify(true)
                    }
                }
                else {
                    Snackbar.make(mytoolbar, "لا يوجد بيانات لتعديلها", Snackbar.LENGTH_LONG).show()
                    modyfy  = false
                }
            }else if(menuitem.itemId == R.id.delete){
                customadapter.setCopy(false)
                customadapter.setModify(false)
                delete = true
                if (tab.isNotEmpty()) {
                    if (delete) {
                        showindication.text = "اظغط على العناصر التي تريد حذفها"
                        stoppp.text = "إلغاء"
                        customadapter.delete = true // delete element from table and sharedPref file
                        if (tab.size == 0) delete = false
                        stoppp.setOnClickListener {
                            showindication.text = ""
                            stoppp.text = ""
                            customadapter.setDelete(false)
                            customadapter.setStop(false)
                            delete = false
                        }
                    }
                } // end if tab is not empty
                else {
                    Snackbar.make(mytoolbar, "لا يوجد بيانات لحذفها", Snackbar.LENGTH_LONG).show()
                    delete = false
                }
            }else if(menuitem.itemId == R.id.deleteall){
                showindication.text = ""
                stoppp.text = ""
                delete = true
                if (tab.isNotEmpty()) {
                    val view2 = View.inflate(this , R.layout.alertdialog2 , null) //iflate de alert_dialog view
                    val builder2 = AlertDialog.Builder(this)
                    builder2.setView(view2)
                    val dialog2 = builder2.create()
                    dialog2.show()
                    dialog2.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog2.setCancelable(false)
                    val getback : Button = view2.findViewById(R.id.goback)
                    val yes : Button = view2.findViewById(R.id.yes)
                    getback.setOnClickListener {
                        dialog2.dismiss()
                        delete = false
                    }
                    yes.setOnClickListener {
                        if (delete) {
                            tab.clear()
                            customadapter.notifyDataSetChanged()
                            delete = false
                            editor.clear() // delete from file
                            editor.apply()
                            editor2.clear()
                            editor2.apply()
                            Snackbar.make(mytoolbar, "تم الحذف الكل بنجاح", Snackbar.LENGTH_LONG).show()
                            showindication.text = ""
                            stoppp.text = ""
                            dialog2.dismiss()
                        }
                    }
                } // end if tab is not empty
                else {
                    Snackbar.make(mytoolbar, "لا يوجد بيانات لحذفها", Snackbar.LENGTH_LONG).show()
                    delete = false
                }
            } // end delete all
            else if (menuitem.itemId == R.id.copy){
                showindication.text = ""
                stoppp.text = ""
                customadapter.setModify(false)
                customadapter.setDelete(false)
                copy = true
                if (copy) {
                    Toast.makeText(this, "إضغط على النص الذي تريد نسخه", Toast.LENGTH_SHORT).show()
                    customadapter.setCopy(true)
                    copy = false
                }
            }
            true
        }
        /** *************************** end Tool bar ************************************** **/

        /** ****************************** Add element ********************************** **/
        pressactionbtn.setOnClickListener{
            customadapter.setModify(false)
            customadapter.setDelete(false)
            customadapter.setCopy(false)
            showindication.text = ""
            stoppp.text = ""
            val view = View.inflate(this , R.layout.alert_dialog , null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)
            val back : Button = view.findViewById(R.id.back)
            back.setOnClickListener {
                dialog.dismiss()
            }
            val send : Button = view.findViewById(R.id.send)
            send.setOnClickListener { // begin listener إضافة
                val plus: Button = view.findViewById(R.id.plus)
                textToShow = plus.text.toString()  // read input
                if (textToShow.isNotEmpty()) { // begin if text is not empty
                    /** Si tu accepte les valeurs doublant ------ **/
                    while (mysharedpreferences.getString(textToShow , "0").toString() == textToShow) textToShow += " "
                   /**  ----------------------------------------- **/
                    tab.add(textToShow)
                    customadapter.notifyDataSetChanged()
                    editor.putString(textToShow , textToShow) // Ex : key = "hello" , value = "hello"
                    editor.apply()
                    thelistv.adapter = customadapter
                    textToShow = ""
                    dialog.dismiss()
                } // end if text is not empty
                else {
                    plus.error = "يرجى كتابة النص"
                }
            } // end listener إضافة
        } // alert dialog 1
        /** ***************************** End Add element ******************************* **/

        /** ***************************** Intent back *********************************** **/
        goback.setOnClickListener {
             super.onBackPressed()
             overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right)
        }
        /** **************************** End Intent back ******************************* **/
    } // end on create
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right)
    }
} // end of class everyday

