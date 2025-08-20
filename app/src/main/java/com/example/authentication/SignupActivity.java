package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener  {

    //    BlurView blurView;
    private Spinner spinner;  // for spinner

    //SIGNUP ATTRIBUTES
    private String firstName, lastName, email, contactNo, parentContactNo, sem, userName,passWord;

    private Button signupBtn, goToLoginBtn;

//    ALL INPUTS
    EditText firstname_input, lastname_input, email_input, contact_input, parent_contact_input;

//    FOR FIREBASE DATABASE
    FirebaseDatabase firedb = FirebaseDatabase.getInstance();
    DatabaseReference ref;

////    New User
//    newUser new_user=new newUser(firstName,lastName,email,contactNo,parentContactNo,sem);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //        FOR SEM SELECTION
        spinner = findViewById(R.id.semester_input);


        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Sem-1");
        arrayList.add("Sem-2");
        arrayList.add("Sem-3");
        arrayList.add("Sem-4");
        arrayList.add("Sem-5");
        arrayList.add("Sem-6");

        ArrayAdapter<String> adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayList
        );
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);


//        FOR SIGNUP BUTTON CLICK
        signupBtn = findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(this);

        goToLoginBtn = findViewById(R.id.gotologin_btn);
        goToLoginBtn.setOnClickListener(this);

//        FOR FIREBASE DATABASE
//        firedb=FirebaseDatabase.getInstance();
        ref=firedb.getReference("New login requests");



    }

    //  FOR SPINNER WIDGET
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedItem = adapterView.getItemAtPosition(i).toString();
        sem = selectedItem;
        Toast.makeText(this,selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // FOR SIGNUP BTN CLICKED
    @Override
    public void onClick(View view) {

        int btnClicked = view.getId();

        if (btnClicked == R.id.gotologin_btn){

            //  IF LOGIN BTN IS CLICKED
            redirectToLogin();

        } else {

            // IF SIGNUP BTN IS CLICKED
            if (checkDetails()) {
                // UPLOAD TO DATABASE
                DatabaseReference new_child = ref.child(userName);
                new_child.setValue(new newUser(firstName,lastName,email,contactNo,parentContactNo,sem,userName,passWord));

                Toast.makeText(this, "Signed Up Successfully!!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Sign Up Failed!!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    // CLASS TO STORE DATA OF THE NEW LOGIN
    static class newUser{
        public String firstName, lastName, email, contactNo, parentContactNo, sem, username, password;

        public newUser(){

        }
        public newUser(String firstName,String lastName,String email,String contactNo,String parentContactNo,String sem, String username, String password){
            this.firstName=firstName;
            this.lastName=lastName;
            this.email=email;
            this.contactNo=contactNo;
            this.parentContactNo=parentContactNo;
            this.sem=sem;
            this.username=username;
            this.password=password;
        }

    }

    // CHECK ALL DETAILS ARE FILLED UP OR NOT
    private boolean checkDetails(){
        firstname_input=findViewById(R.id.firstname_input);
        lastname_input = findViewById(R.id.lastname_input);
        email_input = findViewById(R.id.email_input);
        contact_input = findViewById(R.id.contact_input);
        parent_contact_input = findViewById(R.id.parent_contact_input);

        if (firstname_input == null) {
            Toast.makeText(this, "firstname_input is missing from layout!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastname_input == null) {
            Toast.makeText(this, "lastname_input is missing from layout!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email_input == null) {
            Toast.makeText(this, "email_input is missing from layout!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contact_input == null) {
            Toast.makeText(this, "contact_input is missing from layout!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (parent_contact_input == null) {
            Toast.makeText(this, "parent_contact_input is missing from layout!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinner == null) {
            Toast.makeText(this, "semester_input (spinner) is missing from layout!", Toast.LENGTH_SHORT).show();
            return false;
        }



        firstName = firstname_input.getText().toString().trim();
        lastName = lastname_input.getText().toString().trim();
        email = email_input.getText().toString().trim();
        contactNo = contact_input.getText().toString().trim();
        parentContactNo = parent_contact_input.getText().toString().trim();
        userName=firstName+" "+lastName;
        passWord=contactNo;



        String[] checkStrings = {firstName, lastName, email, contactNo, parentContactNo, sem};
        for (String detail : checkStrings){
            if (detail.isEmpty())
                return false;
        }


        // EVERYTHING IS OK THEN REDIRECT TO LOGIN PAGE
        redirectToLogin();
        return true;

    }

    private void redirectToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);

        Toast.makeText(SignupActivity.this,"Username is 'FirstName LastName'",Toast.LENGTH_LONG).show();
        Toast.makeText(SignupActivity.this,"Password is 'Contact Number'",Toast.LENGTH_LONG).show();

        intent.putExtra("From","Sign up");
        intent.putExtra("Username",userName);
        intent.putExtra("Password",passWord);

        startActivity(intent);
    }


}