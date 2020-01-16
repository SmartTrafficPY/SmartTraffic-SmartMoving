package smarttraffic.smartmoving.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
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

        badSideWalkYellow.setFocusable(true);
        badSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.green));
        badSideWalkRed.setFocusable(false);
        pendienteRed.setFocusable(false);
        pendienteYellow.setFocusable(true);
        pendienteYellow.setBackgroundColor(getResources().getColor(R.color.green));
        carRed.setFocusable(false);
        carYellow.setFocusable(true);
        carYellow.setBackgroundColor(getResources().getColor(R.color.green));
        obstacleRed.setFocusable(false);
        obstacleYellow.setFocusable(true);
        obstacleYellow.setBackgroundColor(getResources().getColor(R.color.green));
        unevenrRed.setFocusable(false);
        unevenYellow.setFocusable(true);
        unevenYellow.setBackgroundColor(getResources().getColor(R.color.green));
        noSideWalkRed.setFocusable(true);
        noSideWalkRed.setBackgroundColor(getResources().getColor(R.color.green));
        noSideWalkYellow.setFocusable(false);
        stairRed.setFocusable(true);
        stairRed.setBackgroundColor(getResources().getColor(R.color.green));
        stairYellow.setFocusable(false);
        noRampRed.setFocusable(true);
        noRampRed.setBackgroundColor(getResources().getColor(R.color.green));
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
                badSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.green));
                badSideWalkRed.setFocusable(false);
                pendienteRed.setFocusable(false);
                pendienteYellow.setFocusable(true);
                pendienteYellow.setBackgroundColor(getResources().getColor(R.color.green));
                carRed.setFocusable(false);
                carYellow.setFocusable(true);
                carYellow.setBackgroundColor(getResources().getColor(R.color.green));
                obstacleRed.setFocusable(false);
                obstacleYellow.setFocusable(true);
                obstacleYellow.setBackgroundColor(getResources().getColor(R.color.green));
                unevenrRed.setFocusable(false);
                unevenYellow.setFocusable(true);
                unevenYellow.setBackgroundColor(getResources().getColor(R.color.green));
                noSideWalkRed.setFocusable(true);
                noSideWalkRed.setBackgroundColor(getResources().getColor(R.color.green));
                noSideWalkYellow.setFocusable(false);
                stairRed.setFocusable(true);
                stairRed.setBackgroundColor(getResources().getColor(R.color.green));
                stairYellow.setFocusable(false);
                noRampRed.setFocusable(true);
                noRampRed.setBackgroundColor(getResources().getColor(R.color.green));
                noRampYellow.setFocusable(false);

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfigReportsActivity.this, HomeActivity.class);
                if(carYellow.isFocusable()) {
                    i.putExtra("carBtnPoi", R.mipmap.carinsidewalk);
                }else{
                    i.putExtra("carBtnPoi",R.mipmap.carredpoi);
                }
                if(badSideWalkYellow.isFocusable()){
                    i.putExtra("sidewalkBtnPoi",R.mipmap.sidewalkwarning);
                }else{
                    i.putExtra("sidewalkBtnPoi",R.mipmap.badsidewalkred);
                }
                if(unevenYellow.isFocusable()){
                    i.putExtra("streetBtnPoi",R.mipmap.uneven);
                }else{
                    i.putExtra("streetBtnPoi",R.mipmap.unevenroadred);
                }
                if(obstacleYellow.isFocusable()){
                    i.putExtra("obstacleBtnPoi",R.mipmap.obstacle);
                }else{
                    i.putExtra("obstacleBtnPoi",R.mipmap.obstaclered);
                }
                if(pendienteYellow.isFocusable()){
                    i.putExtra("pendienteBtnPoi",R.mipmap.pendiente);
                }else{
                    i.putExtra("pendienteBtnPoi",R.mipmap.pendientered);
                }
                if(noRampYellow.isFocusable()){
                    i.putExtra("noramppoi",R.mipmap.norampyellow);
                }else{
                    i.putExtra("noramppoi",R.mipmap.norampredpoii);
                }
                if(noSideWalkYellow.isFocusable()){
                    i.putExtra("nosidewalkpoi",R.mipmap.nosidewalkyellowpoi);
                }else{
                    i.putExtra("nosidewalkpoi",R.mipmap.nosidewalkredpoii);
                }
                if(stairYellow.isFocusable()){
                    i.putExtra("stairpoi",R.mipmap.stairpoiyellow);
                }else{
                    i.putExtra("stairpoi",R.mipmap.stairredpoii);
                }
                startActivity(i);
                finish();

            }
        });

        badSideWalkYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                badSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.green));
                badSideWalkYellow.setFocusable(true);
                badSideWalkRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                badSideWalkRed.setFocusable(false);

            }
        });
        badSideWalkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                badSideWalkRed.setBackgroundColor(getResources().getColor(R.color.green));
                badSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                badSideWalkRed.setFocusable(true); badSideWalkYellow.setFocusable(false);

            }
        });
        pendienteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendienteRed.setBackgroundColor(getResources().getColor(R.color.green));
                pendienteYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                pendienteYellow.setFocusable(false); pendienteRed.setFocusable(true);
            }
        });
        pendienteYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendienteYellow.setBackgroundColor(getResources().getColor(R.color.green));
                pendienteRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                pendienteYellow.setFocusable(true); pendienteRed.setFocusable(false);
            }
        });
        carRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carRed.setBackgroundColor(getResources().getColor(R.color.green)); carYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                carYellow.setFocusable(false); carRed.setFocusable(true);
            }
        });
        carYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carYellow.setBackgroundColor(getResources().getColor(R.color.green)); carRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                carRed.setFocusable(false); carYellow.setFocusable(true);
            }
        });
        obstacleRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obstacleRed.setBackgroundColor(getResources().getColor(R.color.green)); obstacleYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                obstacleYellow.setFocusable(false); obstacleRed.setFocusable(true);
            }
        });
        obstacleYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obstacleYellow.setBackgroundColor(getResources().getColor(R.color.green)); obstacleRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                obstacleRed.setFocusable(false); obstacleYellow.setFocusable(true);
            }
        });
        unevenrRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unevenrRed.setBackgroundColor(getResources().getColor(R.color.green)); unevenYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                unevenYellow.setFocusable(false); unevenrRed.setFocusable(true);
            }
        });
        unevenYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unevenYellow.setBackgroundColor(getResources().getColor(R.color.green)); unevenrRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                unevenrRed.setFocusable(false); unevenYellow.setFocusable(true);
            }
        });
        noSideWalkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noSideWalkRed.setBackgroundColor(getResources().getColor(R.color.green)); noSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                noSideWalkYellow.setFocusable(false); noSideWalkRed.setFocusable(true);
            }
        });
        noSideWalkYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noSideWalkYellow.setBackgroundColor(getResources().getColor(R.color.green)); noSideWalkRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                noSideWalkRed.setFocusable(false); noSideWalkYellow.setFocusable(true);
            }
        });
        stairRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stairRed.setBackgroundColor(getResources().getColor(R.color.green)); stairYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                stairYellow.setFocusable(false); stairRed.setFocusable(true);
            }
        });
        stairYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stairYellow.setBackgroundColor(getResources().getColor(R.color.green)); stairRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                stairRed.setFocusable(false); stairYellow.setFocusable(true);
            }
        });
        noRampRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noRampRed.setBackgroundColor(getResources().getColor(R.color.green)); noRampYellow.setBackgroundColor(getResources().getColor(R.color.transparent));
                noRampYellow.setFocusable(false); noRampRed.setFocusable(true);
            }
        });
        noRampYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noRampYellow.setBackgroundColor(getResources().getColor(R.color.green)); noRampRed.setBackgroundColor(getResources().getColor(R.color.transparent));
                noRampRed.setFocusable(false); noRampYellow.setFocusable(true);
            }
        });

    }

}
