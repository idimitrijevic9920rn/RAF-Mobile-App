package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R
import timber.log.Timber

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            this.isLogged()
            false
        }
    }

    private fun isLogged(){
        val sharedPref =  getSharedPreferences(packageName,Context.MODE_PRIVATE)
        if (sharedPref.contains(MainActivity.NAME_KEY)) {
            Timber.e("shared da")
            this.redirectApp()
        }else{
            Timber.e("shared ne")
            this.redirectLogin()
        }
    }

    private fun redirectApp(){
        val intent = Intent(this@SplashScreenActivity,  AppActivity::class.java)
        this.startActivity(intent)
    }

    private fun redirectLogin(){
        val intent = Intent(this@SplashScreenActivity,  MainActivity::class.java)
        this.startActivity(intent)
    }

}