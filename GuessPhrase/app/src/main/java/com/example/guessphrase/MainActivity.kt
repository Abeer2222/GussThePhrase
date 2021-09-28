package com.example.guessphrase

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var myLayout: ConstraintLayout
    private lateinit var guessInput: EditText
    private lateinit var guessButton: Button
    private lateinit var theList: ArrayList<String>
    private lateinit var textPhrase: TextView
    private lateinit var textLettr: TextView

    private val answer = "to infinity and beyond"
    private val mapOfAnswer = mutableMapOf<Int, Char>()
    private var theAnswer = ""
    private var guessLettr = ""
    private var countG = 0
    private var checkG = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (a in answer.indices) {
            if (answer[a] == ' ') {
                mapOfAnswer[a] = ' '
                theAnswer += ' '
            } else {
                mapOfAnswer[a] = '*'
                theAnswer += '*'
            }
        }

        theList = ArrayList()
        myLayout = findViewById(R.id.clMain)
        rvMain.adapter = PAdapter(this, theList)
        rvMain.layoutManager = LinearLayoutManager(this)

        textPhrase=findViewById(R.id.textPhrase)
        textLettr=findViewById(R.id.textLettr)
        guessInput = findViewById(R.id.etGuess)
        guessButton = findViewById(R.id.btGuess)
        guessButton.setOnClickListener { addGuss() }

        changeText()

    }

    fun addGuss() {
        val input = guessInput.text.toString()
        if (checkG) {
            if (input == answer) {
                disableEntry()
                showAlertDialog("Correct! \n Again?")
            } else {
                theList.add("$input is wrong")
                checkG = false
                changeText()
            }
        } else {
            if (input.isNotEmpty() && input.length == 1) {
                theAnswer = ""
                checkG = true
                checkChar(input[0])
            } else {
                Snackbar.make(clMain, "enter one character please", Snackbar.LENGTH_LONG).show()
            }
        }
        guessInput.text.clear()
        guessInput.clearFocus()
        rvMain.adapter?.notifyDataSetChanged()
    }

    private fun disableEntry() {
        guessButton.isEnabled = false
        guessButton.isClickable = false
        guessInput.isEnabled = false
        guessInput.isClickable = false
    }

    private fun checkChar(guessC: Char) {
        var found = 0
        for (a in answer.indices) {
            if (answer[a] == guessC) {
                mapOfAnswer[a] = guessC
                found++
            }
        }
        for (a in mapOfAnswer) {
            theAnswer += mapOfAnswer[a.key]
        }
        if (theAnswer == answer) {
            disableEntry()
            showAlertDialog("Correct Guess! \n Again?")
        }
        if (guessLettr.isEmpty()) {
            guessLettr +=guessC
        }else{
            guessLettr+= ",, $guessC"
        }
        if(found>0){
            theList.add("Found $found ${guessC.toUpperCase()}(s)")
        }
        else{
            theList.add("No $found ${guessC.toUpperCase()}(s)")
        }
        countG++
        val guessesLeft = 10 - countG
        if(countG<10){theList.add("$guessesLeft guesses remaining")}
        changeText()
        rvMain.scrollToPosition(theList.size - 1)    }

    private fun changeText() {
        textPhrase.text = ("Phrase:  " + theAnswer.toUpperCase()).toString()

        textLettr.text = ("Letters Guessed: $guessLettr").toString()
        if (checkG) {
            guessInput.hint = "Try to guess the phrase"
        } else {
            guessInput.hint = "Try to guess a character"
        }
    }

    private fun showAlertDialog(s: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(s)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()    }


}
