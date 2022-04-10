package m.f.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        tvName.text = intent.getStringExtra(Constants.userName)
        tvScore.text = "Your score is ${intent.getIntExtra(Constants.correctAnsewrs,0)}" +
                " out of ${intent.getIntExtra(Constants.totalQuestions,0)}"
        btnFinish.setOnClickListener(){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}
