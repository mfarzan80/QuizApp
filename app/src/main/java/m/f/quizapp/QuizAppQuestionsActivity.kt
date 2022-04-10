package m.f.quizapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_app_questions.*

class QuizAppQuestionsActivity : AppCompatActivity(),View.OnClickListener {
    private var mCurrentPosition:Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCurrectAnswers: Int = 0
    private var mUserName:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_app_questions)
        mQuestionList = Constants.getQuestions()
        setQuestion()
        mUserName = intent.getStringExtra(Constants.userName)
        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSub.setOnClickListener(this)
    }
    private fun setQuestion(){
        btnSub.text = "SUBMIT"
        val question: Question = mQuestionList!![mCurrentPosition-1]
        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition" + "/" + progressBar.max
        defaultOptionView()
        tvQuestion.text = question.question
        ivImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }
    private fun defaultOptionView(){
        val options = ArrayList<TextView>()
        options.add(0,tvOptionOne)
        options.add(1,tvOptionTwo)
        options.add(2,tvOptionThree)
        options.add(3,tvOptionFour)

        for (option in options){
            option.setTextColor(ContextCompat.getColor(this,R.color.colorPink))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.option)
        }
    }

    override fun onClick(v: View?) {
        if (btnSub.text.toString() == "SUBMIT")
        when(v?.id){
            R.id.tvOptionOne -> selectedOptionView(tvOptionOne,1)
            R.id.tvOptionTwo -> selectedOptionView(tvOptionTwo,2)
            R.id.tvOptionThree -> selectedOptionView(tvOptionThree,3)
            R.id.tvOptionFour -> selectedOptionView(tvOptionFour,4)
            R.id.btnSub ->{
                if (mSelectedOptionPosition == 0){
                   nextQuestion()
                }
                else {
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    answerView(mSelectedOptionPosition,R.drawable.wrong_option)
                    answerView(question!!.correctAnswer,R.drawable.correct_option)
                    if(mSelectedOptionPosition == question.correctAnswer) mCurrectAnswers++
                    if(mCurrentPosition == mQuestionList!!.size)
                        btnSub.text = "FINISH"
                    else btnSub.text = "NEXT"
                }
                mSelectedOptionPosition = 0
            }
        }else if (v?.id == R.id.btnSub)
            nextQuestion()
    }
    private fun nextQuestion(){
        mCurrentPosition++
        if (mCurrentPosition<=mQuestionList!!.size)
            setQuestion()
        else {
            var intent =  Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.userName,mUserName)
            intent.putExtra(Constants.totalQuestions,mQuestionList!!.size)
            intent.putExtra(Constants.correctAnsewrs,mCurrectAnswers)
            startActivity(intent)
            finish()
        }
    }
    private fun answerView(ans:Int,draw:Int){
        when(ans){
            1->{
                tvOptionOne.background = ContextCompat.getDrawable(
                    this,draw
                )
            }
            2->{
                tvOptionTwo.background = ContextCompat.getDrawable(
                    this,draw
                )
            }
            3->{
                tvOptionThree.background = ContextCompat.getDrawable(
                    this,draw
                )
            }
            4->{
                tvOptionFour.background = ContextCompat.getDrawable(
                    this,draw
                )
            }
        }
    }
    private fun selectedOptionView(tv: TextView, son:Int){
        defaultOptionView()
        mSelectedOptionPosition = son
        tv.setTextColor(ContextCompat.getColor
            (this,R.color.darkPurpleTextColor))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,R.drawable.selected_option)

    }
}
