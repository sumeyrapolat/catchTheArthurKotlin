package com.sumeyra.catchthearthur

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.sumeyra.catchthearthur.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score =0
    var imageArrayList = ArrayList<ImageView>()
    var runnable= Runnable{}
    var handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //imageArray yapıyoruz
        imageArrayList.add(binding.imageView)
        imageArrayList.add(binding.imageView2)
        imageArrayList.add(binding.imageView3)
        imageArrayList.add(binding.imageView4)
        imageArrayList.add(binding.imageView5)
        imageArrayList.add(binding.imageView6)
        imageArrayList.add(binding.imageView7)
        imageArrayList.add(binding.imageView8)
        imageArrayList.add(binding.imageView9)


        object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.text= "Left: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                binding.timeText.text = "Left: 0"
                handler.removeCallbacks(runnable)

                for (i in imageArrayList) {
                    i.visibility = View.INVISIBLE
                }


                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Do You Want To Play Again ?")
                alertDialog.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity,"Started",Toast.LENGTH_SHORT).show()
                        //restart the game ?
                        // burada içinde bulunduğumuz aktiviteyi yeniden başlatabiliriz
                        val intentForRestart = intent
                        finish()
                        startActivity(intentForRestart)
                    }
                })
                alertDialog.setNegativeButton("No", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity, "GAME OVER",Toast.LENGTH_SHORT).show()
                        //finish the game ?
                        //burada merak ettiğim kısım yukarıdaki tüm arthurların gizlenmesi ve runnable döngüsünün sona ermesi
                    }
                })
                alertDialog.show()
            }

        }.start()

        hideArthur()
    }
    fun hideArthur(){
        //runnable
        runnable = object:Runnable{
            override fun run() {
                for (i in imageArrayList) {
                    i.visibility = View.INVISIBLE
                }
                    val random = Random()
                    val randomIndex = random.nextInt(9)
                    imageArrayList[randomIndex].visibility = View.VISIBLE

                    handler.postDelayed(runnable,500)

            }

        }
        handler.post(runnable)

    }

    fun increaseScore(view : View){
        score++
        binding.scoreText.text ="Score : ${score}"
    }

}