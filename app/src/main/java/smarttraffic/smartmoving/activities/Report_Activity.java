package smarttraffic.smartmoving.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import smarttraffic.smartmoving.R;

public class Report_Activity extends AppCompatActivity {

    @BindView(R.id.curbramppoi)
    ImageButton rampBtnPoi;
    @BindView(R.id.bustoppoi)
    ImageButton busstoBtnPoi;
    @BindView(R.id.carpoi)
    ImageButton carBtnPoi;
    @BindView(R.id.sidewalkpoi)
    ImageButton sidewalkBtnPoi;
    @BindView(R.id.obstaclepoi)
    ImageButton obstacleBtnPoi;
    @BindView(R.id.pendientepoi)
    ImageButton pendienteBtnPoi;
    @BindView(R.id.streetpoi)
    ImageButton streetBtnPoi;
    @BindView(R.id.closebutton)
    ImageButton closeWindowBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_layout);
        ButterKnife.bind(this);

        rampBtnPoi.setTag(1);
        busstoBtnPoi.setTag(2);
        carBtnPoi.setTag(3);
        sidewalkBtnPoi.setTag(4);
        obstacleBtnPoi.setTag(5);
        pendienteBtnPoi.setTag(6);
        streetBtnPoi.setTag(7);
        ImageButton.OnLongClickListener olongBtn = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Report_Activity.this, HomeActivity.class);
                intent.putExtra("items", String.valueOf(v.getTag()));
                startActivity(intent);
                return true;
            }
        };
        rampBtnPoi.setOnLongClickListener(olongBtn);
        busstoBtnPoi.setOnLongClickListener(olongBtn);
        carBtnPoi.setOnLongClickListener(olongBtn);
        sidewalkBtnPoi.setOnLongClickListener(olongBtn);
        obstacleBtnPoi.setOnLongClickListener(olongBtn);
        pendienteBtnPoi.setOnLongClickListener(olongBtn);
        streetBtnPoi.setOnLongClickListener(olongBtn);

        closeWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Report_Activity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }



}
