// Java 1001_Sakshi and Pruthvi App Cambrian

package com.example.pruthvi_score


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var teamOneRun = 0
    private var teamOneWicket = 0
    private var teamTwoRun = 0
    private var teamTwoWicket = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val teamOneUp = findViewById<Button>(R.id.TeamOneIncrease)
        val teamOneDown = findViewById<Button>(R.id.TeamOneReduce)
        val teamTwoUp = findViewById<Button>(R.id.TeamTwoIncrease)
        val teamTwoDown = findViewById<Button>(R.id.TeamTwoReduce)
        val newGame = findViewById<Button>(R.id.newGame)

        updateScreen()

        teamOneUp.setOnClickListener(this)
        teamOneDown.setOnClickListener(this)
        teamTwoUp.setOnClickListener(this)
        teamTwoDown.setOnClickListener(this)
        newGame.setOnClickListener(this)

        val scoringOptions = findViewById<RadioGroup>(R.id.ScoringOptions)
        scoringOptions.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int -> updateScreen() }
    }

    override fun onClick(view: View) {
        var runsAndWicket: IntArray

        val teamOneScore = findViewById<TextView>(R.id.TeamOneScore)
        runsAndWicket = getRunAndWickets(teamOneScore.text.toString())
        teamOneRun = runsAndWicket.getOrElse(0) { 0 }
        teamOneWicket = runsAndWicket.getOrElse(1) { 0 }

        val teamTwoScore = findViewById<TextView>(R.id.TeamTwoScore)
        runsAndWicket = getRunAndWickets(teamTwoScore.text.toString())
        teamTwoRun = runsAndWicket.getOrElse(0) { 0 }
        teamTwoWicket = runsAndWicket.getOrElse(1) { 0 }

        val scoreValue = scoringValue
        when (view.id) {
            R.id.TeamOneIncrease ->
                if (scoreValue != -1) {
                    teamOneRun += scoreValue
                } else {
                    teamOneWicket += 1
                }
            R.id.TeamOneReduce ->
                if (scoreValue != -1) {
                    teamOneRun -= scoreValue
                } else {
                    teamOneWicket -= 1
                }
            R.id.TeamTwoIncrease ->
                if (scoreValue != -1) {
                    teamTwoRun += scoreValue
                } else {
                    teamTwoWicket += 1
                }
            R.id.TeamTwoReduce ->
                if (scoreValue != -1) {
                    teamTwoRun -= scoreValue
                } else {
                    teamTwoWicket -= 1
                }
            R.id.newGame -> {
                teamOneRun = 0
                teamTwoRun = 0
                teamOneWicket = 0
                teamTwoWicket = 0
            }
        }
        updateScreen()
    }
    fun updateScreen() {
        //declaring buttons
        val teamOneUp = findViewById<Button>(R.id.TeamOneIncrease)
        val teamOneDown = findViewById<Button>(R.id.TeamOneReduce)
        val teamTwoUp = findViewById<Button>(R.id.TeamTwoIncrease)
        val teamTwoDown = findViewById<Button>(R.id.TeamTwoReduce)
        val newGame = findViewById<Button>(R.id.newGame)

        teamOneDown.isEnabled = teamOneRun > 0 && teamOneWicket < 10
        teamTwoDown.isEnabled = teamTwoRun > 0 && teamTwoWicket < 10
        teamOneUp.isEnabled = teamOneWicket < 10
        teamTwoUp.isEnabled = teamTwoWicket < 10

        if (scoringValue == -1) {
            teamOneDown.isEnabled = teamOneWicket > 0
            teamTwoDown.isEnabled = teamTwoWicket > 0
        }

        if (teamOneRun > 0 || teamTwoRun > 0 || teamOneWicket > 0 || teamTwoWicket > 0) newGame.visibility =
            View.VISIBLE else newGame.visibility = View.GONE

        val teamOneScore = findViewById<TextView>(R.id.TeamOneScore)
        teamOneScore.text = getScore(teamOneRun, teamOneWicket)

        val teamTwoScore = findViewById<TextView>(R.id.TeamTwoScore)
        teamTwoScore.text = getScore(teamTwoRun, teamTwoWicket)
    }


    fun getRunAndWickets(score: String): IntArray {

        val runAndWicketString: Array<String>

        runAndWicketString = score.split("/").toTypedArray()
        val runAndWicket = IntArray(runAndWicketString.size)
        for (i in runAndWicketString.indices) {
            runAndWicket[i] = runAndWicketString[i].toInt()
        }
        return runAndWicket
    }

    val scoringValue: Int
        get() {
            val scoreValue: Int
            val scoringOptions = findViewById<RadioGroup>(R.id.ScoringOptions)
            scoreValue = when (scoringOptions.checkedRadioButtonId) {
                R.id.ScoreOne -> 1
                R.id.ScoreTwo -> 2
                R.id.ScoreThree -> 3
                R.id.ScoreFour -> 4
                R.id.ScoreFive -> 6
                R.id.Wicket -> -1
                else -> 0
            }
            return scoreValue
        }

    fun getScore(run: Int, wicket: Int): String {
        var run = run
        var wicket = wicket
        if (run < 0) {
            run = 0
        }
        if (wicket < 0) {
            wicket = 0
        }
        return if (wicket == 10) {
            run.toString()
        } else
        {
            "$run/$wicket"
        }
    }
}
