package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R


class MainActivity : AppCompatActivity() {


    companion object {
        val NAME_KEY = "username"
        val PIN_KEY = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val composeHolder = findViewById<ComposeView>(R.id.composeHolder)
        composeHolder.setContent {
            LoginScreen()
        }
    }



    @Composable
    fun LoginScreen(): Boolean{
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val name = remember { mutableStateOf(TextFieldValue()) }
            val pin = remember { mutableStateOf(TextFieldValue()) }

            Text(text = "Login")
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Name") },
                value = name.value,
                onValueChange = { name.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "PIN") },
                value = pin.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { pin.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        login(name.value.text,pin.value.text)
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Login")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

        }

        return false
    }

    fun login(name: String, pin: String) {

        if(!name.equals("admin") && !pin.equals("123")){
            Toast.makeText(this, "Wrong credentialas", Toast.LENGTH_SHORT)
            return
        }

        val sharedPref =  getSharedPreferences(packageName,Context.MODE_PRIVATE)
        if(sharedPref!=null){
            sharedPref.edit().clear().apply()
            val editor = sharedPref.edit()
            editor.putString(NAME_KEY, name)
            editor.putString(PIN_KEY, pin)
            editor.apply()
            redirect()
        }
    }


    fun redirect(){
        val intent = Intent(this@MainActivity,  AppActivity::class.java)
        this.startActivity(intent)
    }

    @Preview
    @Composable
    fun LoginPreview() {
        LoginScreen()
    }

}