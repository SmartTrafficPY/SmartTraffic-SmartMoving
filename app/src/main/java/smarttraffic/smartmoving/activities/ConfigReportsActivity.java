package smarttraffic.smartmoving.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import smarttraffic.smartmoving.Constants;
import smarttraffic.smartmoving.R;

public class ConfigReportsActivity extends AppCompatActivity {

    @BindView(R.id.badsidewalkyellow)
    ImageButton badSideWalkYellow;
    @BindView(R.id.badsidewalkred)
    ImageButton badSideWalkRed;
    @BindView(R.id.pendienteyellow)
    ImageButton pendienteYellow;
    @BindView(R.id.pendientered)
    ImageButton pendienteRed;
    @BindView(R.id.caryellow)
    ImageButton carYellow;
    @BindView(R.id.carred)
    ImageButton carRed;
    @BindView(R.id.obstacleyellow)
    ImageButton obstacleYellow;
    @BindView(R.id.obstaclered)
    ImageButton obstacleRed;
    @BindView(R.id.unevenroadyellow)
    ImageButton unevenYellow;
    @BindView(R.id.unevenred)
    ImageButton unevenrRed;
    @BindView(R.id.nosidewalkyellow)
    ImageButton noSideWalkYellow;
    @BindView(R.id.nosidewalkred)
    ImageButton noSideWalkRed;
    @BindView(R.id.stairyellow)
    ImageButton stairYellow;
    @BindView(R.id.stairred)
    ImageButton stairRed;
    @BindView(R.id.norampyellow)
    ImageButton noRampYellow;
    @BindView(R.id.norampred)
    ImageButton noRampRed;
    @BindView(R.id.defaultbtn)
    Button defaultBtn;
    @BindView(R.id.savebtn)
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_reports_layout);
        ButterKnife.bind(this);
        SharedPreferences sharedReportsSevere = getApplicationContext().getSharedPreferences(
                Constants.REPORT_SERVERE, Context.MODE_PRIVATE);
        SharedPreferences sharedReportsLight = getApplicationContext().getSharedPreferences(
                Constants.REPORT_LIGHT,Context.MODE_PRIVATE);
        sharedReportsLight.getAll().clear();
        sharedReportsSevere.getAll().clear();
        final SharedPreferences.Editor editsevere = sharedReportsSevere.edit();
        final SharedPreferences.Editor edittlight = sharedReportsLight.edit();
        editsevere.clear();
        edittlight.clear();
        badSideWalkYellow.setFocusable(true);
        badSideWalkYellow.setBackground(getResources().getDrawable(R.drawable.border));
       // badSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.green));
        badSideWalkRed.setFocusable(false);
        pendienteRed.setFocusable(false);
        pendienteYellow.setFocusable(true);
        pendienteYellow.setBackground(getResources().getDrawable(R.drawable.border));
        //pendienteYellow.setBackgroundColor(getResources().getColor(R.color.green));
        carRed.setFocusable(false);
        carYellow.setFocusable(true);
        carYellow.setBackground(getResources().getDrawable(R.drawable.border));
        //carYellow.setBackgroundColor(getResources().getColor(R.color.green));
        obstacleRed.setFocusable(false);
        obstacleYellow.setFocusable(true);
        obstacleYellow.setBackground(getResources().getDrawable(R.drawable.border));
        //obstacleYellow.setBackgroundColor(getResources().getColor(R.color.green));
        unevenrRed.setFocusable(false);
        unevenYellow.setFocusable(true);
        unevenYellow.setBackground(getResources().getDrawable(R.drawable.border));
        //unevenYellow.setBackgroundColor(getResources().getColor(R.color.green));
        noSideWalkRed.setFocusable(true);
        noSideWalkRed.setBackground(getResources().getDrawable(R.drawable.border));
        //noSideWalkRed.setBackgroundColor(getResources().getColor(R.color.green));
        noSideWalkYellow.setFocusable(false);
        stairRed.setFocusable(true);
        stairRed.setBackground(getResources().getDrawable(R.drawable.border));
        //stairRed.setBackgroundColor(getResources().getColor(R.color.green));
        stairYellow.setFocusable(false);
        noRampRed.setFocusable(true);
        noRampRed.setBackground(getResources().getDrawable(R.drawable.border));
        //noRampRed.setBackgroundColor(getResources().getColor(R.color.green));
        noRampYellow.setFocusable(false);
        badSideWalkRed.setBackgroundColor(0);
        pendienteRed.setBackgroundColor(0);
        carRed.setBackgroundColor(0);
        obstacleRed.setBackgroundColor(0);
        unevenrRed.setBackgroundColor(0);
        noSideWalkYellow.setBackgroundColor(0);
        stairYellow.setBackgroundColor(0);
        noRampYellow.setBackgroundColor(0);



        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                badSideWalkRed.setBackgroundColor(0);
                pendienteRed.setBackgroundColor(0);
                carRed.setBackgroundColor(0);
                obstacleRed.setBackgroundColor(0);
                unevenrRed.setBackgroundColor(0);
                noSideWalkYellow.setBackgroundColor(0);
                stairYellow.setBackgroundColor(0);
                noRampYellow.setBackgroundColor(0);
                badSideWalkYellow.setFocusable(true);

                badSideWalkYellow.setBackground(getResources().getDrawable(R.drawable.border));
                badSideWalkRed.setFocusable(false);
                pendienteRed.setFocusable(false);
                pendienteYellow.setFocusable(true);
                pendienteYellow.setBackground(getResources().getDrawable(R.drawable.border));
                carRed.setFocusable(false);
                carYellow.setFocusable(true);
                carYellow.setBackground(getResources().getDrawable(R.drawable.border));
                obstacleRed.setFocusable(false);
                obstacleYellow.setFocusable(true);
                obstacleYellow.setBackground(getResources().getDrawable(R.drawable.border));
                unevenrRed.setFocusable(false);
                unevenYellow.setFocusable(true);
                unevenYellow.setBackground(getResources().getDrawable(R.drawable.border));
                noSideWalkRed.setFocusable(true);
                noSideWalkRed.setBackground(getResources().getDrawable(R.drawable.border));
                noSideWalkYellow.setFocusable(false);
                stairRed.setFocusable(true);
                stairRed.setBackground(getResources().getDrawable(R.drawable.border));
                stairYellow.setFocusable(false);
                noRampRed.setFocusable(true);
                noRampRed.setBackground(getResources().getDrawable(R.drawable.border));
                noRampYellow.setFocusable(false);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ConfigReportsActivity.this, HomeActivity.class);
                if(carYellow.isFocusable()) {
                    i.putExtra("carBtnPoiY", R.mipmap.carinsidewalk);
                }else{
                    i.putExtra("carBtnPoiR",R.mipmap.carredpoi);
                    editsevere.putString("CARRED","6, ");
                }
                if(badSideWalkYellow.isFocusable()){
                    i.putExtra("sidewalkBtnPoiY",R.mipmap.sidewalkwarning);
                    edittlight.putString("BADSW","2, ");
                }else{
                    i.putExtra("sidewalkBtnPoiR",R.mipmap.badsidewalkred);
                    editsevere.putString("BADSW","2, ");
                }
                if(unevenYellow.isFocusable()){
                    i.putExtra("streetBtnPoiY",R.mipmap.uneven);
                    edittlight.putString("UNEVEM","8, ");
                }else{
                    i.putExtra("streetBtnPoiR",R.mipmap.unevenroadred);
                    editsevere.putString("UNEVEN","8, ");
                }
                if(obstacleYellow.isFocusable()){
                    i.putExtra("obstacleBtnPoiY",R.mipmap.obstacle);
                    edittlight.putString("OBSTACLE","7, ");
                }else{
                    i.putExtra("obstacleBtnPoiR",R.mipmap.obstaclered);
                    editsevere.putString("OBSTACLE","7, ");
                }
                if(pendienteYellow.isFocusable()){
                    i.putExtra("pendienteBtnPoiY",R.mipmap.pendiente);
                    edittlight.putString("PEND","4, ");
                }else{
                    i.putExtra("pendienteBtnPoiR",R.mipmap.pendientered);
                    editsevere.putString("PEND","4, ");
                }
                if(noRampYellow.isFocusable()){
                    i.putExtra("noramppoiY",R.mipmap.norampyellow);
                    edittlight.putString("NORMP","10, ");
                }else{
                    i.putExtra("noramppoiR",R.mipmap.norampredpoii);
                    editsevere.putString("NORMP","10, ");
                }
                if(noSideWalkYellow.isFocusable()){
                    i.putExtra("nosidewalkpoiY",R.mipmap.nosidewalkyellowpoi);
                    edittlight.putString("NOSDW","9, ");
                }else{
                    i.putExtra("nosidewalkpoiR",R.mipmap.nosidewalkredpoii);
                    editsevere.putString("NOSDW","9, ");
                }
                if(stairYellow.isFocusable()){
                    i.putExtra("stairpoiY",R.mipmap.stairpoiyellow);
                    edittlight.putString("STAIR","1, ");
                }else{
                    i.putExtra("stairpoiR",R.mipmap.stairredpoii);
                    editsevere.putString("STAIR","1, ");
                }
                editsevere.apply();
                edittlight.apply();
                startActivity(i);
                finish();

            }
        });

        badSideWalkYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                badSideWalkYellow.setBackground(getResources().getDrawable(R.drawable.border));
                badSideWalkYellow.setFocusable(true);
                badSideWalkRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                badSideWalkRed.setFocusable(false);

            }
        });
        badSideWalkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                badSideWalkRed.setBackground(getResources().getDrawable(R.drawable.border));
                badSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                badSideWalkRed.setFocusable(true); badSideWalkYellow.setFocusable(false);

            }
        });
        pendienteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendienteRed.setBackground(getResources().getDrawable(R.drawable.border));
                pendienteYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                pendienteYellow.setFocusable(false); pendienteRed.setFocusable(true);
            }
        });
        pendienteYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendienteYellow.setBackground(getResources().getDrawable(R.drawable.border));
                pendienteRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                pendienteYellow.setFocusable(true); pendienteRed.setFocusable(false);
            }
        });
        carRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carRed.setBackground(getResources().getDrawable(R.drawable.border)); carYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                carYellow.setFocusable(false); carRed.setFocusable(true);
            }
        });
        carYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carYellow.setBackground(getResources().getDrawable(R.drawable.border)); carRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                carRed.setFocusable(false); carYellow.setFocusable(true);
            }
        });
        obstacleRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obstacleRed.setBackground(getResources().getDrawable(R.drawable.border)); obstacleYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                obstacleYellow.setFocusable(false); obstacleRed.setFocusable(true);
            }
        });
        obstacleYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obstacleYellow.setBackground(getResources().getDrawable(R.drawable.border)); obstacleRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                obstacleRed.setFocusable(false); obstacleYellow.setFocusable(true);
            }
        });
        unevenrRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unevenrRed.setBackground(getResources().getDrawable(R.drawable.border)); unevenYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                unevenYellow.setFocusable(false); unevenrRed.setFocusable(true);
            }
        });
        unevenYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unevenYellow.setBackground(getResources().getDrawable(R.drawable.border)); unevenrRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                unevenrRed.setFocusable(false); unevenYellow.setFocusable(true);
            }
        });
        noSideWalkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noSideWalkRed.setBackground(getResources().getDrawable(R.drawable.border)); noSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                noSideWalkYellow.setFocusable(false); noSideWalkRed.setFocusable(true);
            }
        });
        noSideWalkYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noSideWalkYellow.setBackground(getResources().getDrawable(R.drawable.border)); noSideWalkRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                noSideWalkRed.setFocusable(false); noSideWalkYellow.setFocusable(true);
            }
        });
        stairRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stairRed.setBackground(getResources().getDrawable(R.drawable.border)); stairYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                stairYellow.setFocusable(false); stairRed.setFocusable(true);
            }
        });
        stairYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stairYellow.setBackground(getResources().getDrawable(R.drawable.border)); stairRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                stairRed.setFocusable(false); stairYellow.setFocusable(true);
            }
        });
        noRampRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noRampRed.setBackground(getResources().getDrawable(R.drawable.border)); noRampYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                noRampYellow.setFocusable(false); noRampRed.setFocusable(true);
            }
        });
        noRampYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noRampYellow.setBackground(getResources().getDrawable(R.drawable.border)); noRampRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                noRampRed.setFocusable(false); noRampYellow.setFocusable(true);
            }
        });

    }

}
