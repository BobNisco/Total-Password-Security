
package com.simplrlabs.totalpassword;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class Check extends SherlockFragment {
    String strVerdict;
    TextView result;
    EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.check, container, false);
        password = (EditText) view.findViewById(R.id.password_input);
        result = (TextView) view.findViewById(R.id.result);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwd = password.getText().toString();
                testPassword(passwd);
                result.setText(strVerdict);
            }
        });

        return view;
    }
    
    public String testPassword(String test) {
        int intScore = 0;
        int lower = 0;
        int numbers = 0;
        int special = 0;
        int length = 0;
        int upper = 0;
        ArrayList<String> worstPasswords = new ArrayList<String>();
        /* Could use double brace initialization, but this way
         * has faster execution time.
         * Reference: http://stackoverflow.com/questions/924285/efficiency-of-java-double-brace-initialization
         */
        
        // Worst passwords of 2012
        // Source: http://www.cnn.com/2012/10/25/tech/web/worst-passwords-2012/index.html
        worstPasswords.add("password");
        worstPasswords.add("123456");
        worstPasswords.add("12345678");
        worstPasswords.add("abc123");
        worstPasswords.add("qwerty");
        worstPasswords.add("monkey");
        worstPasswords.add("letmein");
        worstPasswords.add("dragon");
        worstPasswords.add("111111");
        worstPasswords.add("baseball");
        worstPasswords.add("iloveyou");
        worstPasswords.add("trustno1");
        worstPasswords.add("1234567");
        worstPasswords.add("sunshine");
        worstPasswords.add("master");
        worstPasswords.add("123123");
        worstPasswords.add("welcome");
        worstPasswords.add("shadow");
        worstPasswords.add("ashley");
        worstPasswords.add("football");
        worstPasswords.add("jesus");
        worstPasswords.add("michael");
        worstPasswords.add("ninja");
        worstPasswords.add("mustang");
        worstPasswords.add("password1");
        
        boolean isWorst = false;
        for (String p : worstPasswords) {
            if (p.equals(test))
                isWorst = true;
        }
        if (!isWorst) {
            // Password Length
            length = test.length();
            if (length < 5) {
                intScore += 3;
            } else if ((length > 4) && (test.length() < 8)) {
                intScore += 6;
            }
            else if ((length > 7) && (test.length() < 16)) {
                intScore += 12;
            } else if (length > 15) {
                intScore += 18;
            }
    
            // Letters
            Pattern p = Pattern.compile(".??[a-z]");
            Matcher m = p.matcher(test);
            while (m.find()) {
                lower++;
            }
            if (lower > 0) {
                intScore++;
            }
            p = Pattern.compile(".??[A-Z]");
            m = p.matcher(test);
            while (m.find()) {
                upper++;
            }
            if (upper > 0) {
                intScore += 5;
            }
    
            p = Pattern.compile(".??[0-9]");
            m = p.matcher(test);
            while (m.find()) {
                numbers++;
            }
            if (numbers > 0) {
                intScore += 5;
                if (numbers > 1) {
                    intScore += 2;
                    if (numbers > 2) {
                        intScore += 3;
                    }
                }
            }
    
            p = Pattern.compile(".??[:,!,@,#,$,%,^,&,*,?,_,~]");
            m = p.matcher(test);
            while (m.find()) {
                special++;
            }
            if (special > 0) {
                intScore += 5;
                if (special > 1) {
                    intScore += intScore + 5;
                }
            }
    
            if ((upper > 0) && (lower > 0))
                intScore += 2;
            if (((upper > 0) || (lower > 0)) && (numbers > 0))
                intScore += 2;
            if (((upper > 0) || (lower > 0)) && (numbers > 0) && (special > 0))
                intScore += 2;
            if ((upper > 0) && (lower > 0) && (numbers > 0) && (special > 0))
                intScore += 2;
        }
        
        if (isWorst)
               strVerdict = "One of top 25 most common passwords";
        else if (intScore < 16)
            strVerdict = "Very Weak";
        else if ((intScore > 15) && (intScore < 25))
            strVerdict = "Weak";
        else if ((intScore > 24) && (intScore < 35))
            strVerdict = "Mediocre";
        else if ((intScore > 34) && (intScore < 45))
            strVerdict = "Strong";
        else
            strVerdict = "Very Strong";

        return strVerdict;
    }
}
