package smarttraffic.smartmoving.activities;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import smarttraffic.smartmoving.R;
import smarttraffic.smartmoving.Receivers.RegistrationReceiver;
import smarttraffic.smartmoving.services.RegistrationService;


public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.usernameSignUp)
    EditText usernameInput;
    @BindView(R.id.birthDate)
    TextView birthDate;
    @BindView(R.id.passwordSignUp)
    EditText passwordInput;
    @BindView(R.id.maleRadButton)
    RadioButton maleRadButton;
    @BindView(R.id.femaleRadButton)
    RadioButton femaleRadButton;
    @BindView(R.id.btndatepick)
    ImageButton datePickerButton;
    @BindView(R.id.sexRadioGroup)
    RadioGroup sexSelectRadioGroup;
    @BindView(R.id.wheelchairRadButton)
    RadioButton wheelchairBtn;
    @BindView(R.id.noneRadButton)
    RadioButton noneBtn;
    @BindView(R.id.babycarriageRadButton)
    RadioButton babyCarriageBtn;
    @BindView(R.id.crutchRadButton)
    RadioButton crutchBtn;
    @BindView(R.id.stickRadButton)
    RadioButton stickBtn;
    @BindView(R.id.walkerRadButton)
    RadioButton walkerBtn;
    @BindView(R.id.typemovementradio)
    RadioGroup typeMovementRadioGroup;
    @BindView(R.id.signUpButton)
    Button signInBtn;
    @BindView(R.id.btnshowpass)
    ImageButton passwordModeBtn;


    public final Calendar calendar = Calendar.getInstance();
    final int actuallMonth = calendar.get(Calendar.MONTH);
    final int actuallDay = calendar.get(Calendar.DAY_OF_MONTH);
    final int actuallYear = calendar.get(Calendar.YEAR);
    private boolean showPasswordText = false;
    private static final String CERO = "0";
    private static final String GUION = "-";
    private static final String LOG_TAG = "RegistryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
//        View _mw = getLayoutInflater().inflate(R.layout.register_layout, null);
//        setContentView(_mw);
        ButterKnife.bind(this);



        IntentFilter filter = new IntentFilter();
        filter.addAction(RegistrationService.REGISTRATION_OK);
        filter.addAction(RegistrationService.BAD_REGISTRATION);
        RegistrationReceiver registrationReceiver = new RegistrationReceiver();
        registerReceiver(registrationReceiver, filter);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getDatePickedUp();

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRegister();
            }
        });
        /*goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginActivity();
            }
        });*/
        passwordModeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                //TODO: if image is showText set TO dont't ShowText
                // and set password text Visual for user...
                if(!showPasswordText){
                    passwordModeBtn.setImageDrawable(
                            context.getDrawable(R.drawable.dontshowtext));
                    //Show Password:
                    passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPasswordText = !showPasswordText;
                }else{
                    passwordModeBtn.setImageDrawable(context.getDrawable(R.drawable.showtext));
                    //Hide Password:
                    passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPasswordText = !showPasswordText;
                }
            }
        });
    }
   private void gotoLoginActivity(){
        Intent i = new Intent (RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }
    @RequiresApi(api= Build.VERSION_CODES.N)
    private void getDatePickedUp(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                birthDate.setText(year + GUION + mesFormateado + GUION + diaFormateado);
            }

        }, actuallYear, actuallMonth, actuallDay);
        datePickerDialog.show();
    }
    private void createRegister(){
        Log.d(LOG_TAG, "User trying to resgistry");
        signInBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando el registro...");

        if(dataIsCorrectlyComplete()){
            sendRegistrationPetition();
            progressDialog.show();
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    signInBtn.setEnabled(true);
                    progressDialog.dismiss();
                }
            }, 3000);
        }
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean dataIsCorrectlyComplete(){

            if(passwordInput.getText().toString().length() > 5){
                if(maleRadButton.isChecked() || femaleRadButton.isChecked()){
                    if(!birthDate.getText().toString().isEmpty()){
                        return true;
                    }else{
                        showToast("Completar fecha de nacimiento!");
                        return false;
                    }
                }else{
                    showToast("Completar sexo");
                    return false;
                }
            }else{
                showToast("Contraseña muy corta!");
                return false;
            }
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.smicono);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    private void sendRegistrationPetition(){
        Intent registerIntent = new Intent(RegisterActivity.this, RegistrationService.class);
        registerIntent.putExtra("username", usernameInput.getText().toString());
        registerIntent.putExtra("password", passwordInput.getText().toString());
        registerIntent.putExtra("birth_date", birthDate.getText().toString());
        if(onRadioButtonClickedSX() != null){
            registerIntent.putExtra("sex", onRadioButtonClickedSX());
        }
        if(onRadioButtonClickedTM() != null){
            registerIntent.putExtra("type_movement", onRadioButtonClickedTM());
        }
        startService(registerIntent);
    }
    public String onRadioButtonClickedSX(){
        if(femaleRadButton.isChecked()){
            return "F";
        }else if(maleRadButton.isChecked()){
            return "M";
        }else{
            return null;
        }
    }
    public Integer onRadioButtonClickedTM(){
        if(wheelchairBtn.isChecked()){
            return 3;
        }else if(babyCarriageBtn.isChecked()){
            return 4;
        }else if(walkerBtn.isChecked()){
            return 2;
        }else if (stickBtn.isChecked()){
            return 5;
        }else if(noneBtn.isChecked()){
            return 7;
        }else if(crutchBtn.isChecked()){
            return 6;
        }else{
            return null;
        }
    }
}


