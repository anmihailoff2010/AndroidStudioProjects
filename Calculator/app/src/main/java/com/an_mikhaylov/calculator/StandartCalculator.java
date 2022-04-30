package com.an_mikhaylov.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.an_mikhaylov.calculator.db.DBHelper;

public class StandartCalculator extends AppCompatActivity {

    private TextView tv1, tv2;
    private TextView tv3 = null;
    private Double num1;
    private boolean hasDot;
    private boolean isNew = true;
    private double memory = 0;
    private String operator = "";
    private String number1;
    private SharedPreferences pref;
    final String SAVED_TEXT1 = "saved_text1";
    final String SAVED_TEXT2= "saved_text2";
    private DBHelper dbHelper;
    private String expression="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standart_calculator);
        init();
        loadText();

    }

    private void init() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        hasDot = false;
        dbHelper = new DBHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.engineeringCalculator) {
            Intent i = new Intent(StandartCalculator.this, EngineeringCalculator.class);
            startActivity(i);
        } else if (id == R.id.history) {
            Intent i = new Intent(StandartCalculator.this, HistoryActivity.class);
            i.putExtra("calcName", "STANDART");
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnClick_number(View view) {
        if (isNew)
            tv1.setText("");
        isNew = false;
        String number = tv1.getText().toString();
        switch (view.getId()) {
            case R.id.bu0:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = "0";
                } else {
                    number = number + "0";
                }
                break;
            case R.id.bu1:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "1";
                break;
            case R.id.bu2:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "2";
                break;
            case R.id.bu3:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "3";
                break;
            case R.id.bu4:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "4";
                break;
            case R.id.bu5:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "5";
                break;
            case R.id.bu6:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "6";
                break;
            case R.id.bu7:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "7";
                break;
            case R.id.bu8:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "8";
                break;
            case R.id.bu9:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "9";
                break;
            case R.id.buDecimalPoint:
                if (decimalPointIsPresent(number)) {
                } else {
                    number = number + ".";
                }
                break;
        }
        tv1.setText(number);
    }

    private boolean zeroIsFirst(String number) {
        if (number.equals("")) {
            return true;
        }
        if (number.charAt(0) == '0') {
            return true;
        } else {
            return false;
        }
    }

    public boolean decimalPointIsPresent(String number) {
        if (number.indexOf(".") == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void onClickOperation(View view) {
        isNew = true;
        number1 = tv1.getText().toString();
        switch (view.getId()) {
            case R.id.buMultiplication:
                operator = "✕";
                break;
            case R.id.buDel:
                operator = "÷";
                break;
            case R.id.buMinus:
                operator = "-";
                break;
            case R.id.buPlus:
                operator = "+";
                break;
            case R.id.buSquaring:
                operator = "x²";
                break;
            case R.id.buCubing:
                operator = "x³";
                break;
            case R.id.buXy:
                operator = "xⁿ";
                break;
        }
    }

    public void onClickEqual(View view) {
        String number2 = tv1.getText().toString();
        Double result = 0.0;
            switch (operator) {
                case "✕":
                    result = Double.parseDouble(number1) * Double.parseDouble(number2);
                    tv2.setText(number1 + " " + operator + " " + number2 + " = ");
                    tv1.setText(result + "");
                    break;
                case "÷":
                    if (Double.parseDouble(number2) == 0) {
                        tv1.setText("На ноль делить нельзя!!!");
                    } else {
                        result = Double.parseDouble(number1) / Double.parseDouble(number2);
                        tv2.setText(number1 + " " + operator + " " + number2 + " = ");
                        tv1.setText(result + "");
                    }
                    break;
                case "+":
                    result = Double.parseDouble(number1) + Double.parseDouble(number2);
                    tv2.setText(number1 + " " + operator + " " + number2 + " = ");
                    tv1.setText(result + "");
                    break;
                case "-":
                    result = Double.parseDouble(number1) - Double.parseDouble(number2);
                    tv2.setText(number1 + " " + operator + " " + number2 + " = ");
                    tv1.setText(result + "");
                    break;
                case "x²":
                    result = Math.pow(Double.parseDouble(number1), 2);
                    tv2.setText(number1 + " ^ 2" + " = ");
                    tv1.setText(result + "");
                    break;
                case "x³":
                    result = Math.pow(Double.parseDouble(number1), 3);
                    tv2.setText(number1 + " ^ 3" + " = ");
                    tv1.setText(result + "");
                    break;
                case "xⁿ":
                    result = Math.pow(Double.parseDouble(number1), Double.parseDouble(number2));
                    tv2.setText(number1 + " ^ " + number2 + " = ");
                    tv1.setText(result + "");
                    break;
            }
            expression = tv2.getText().toString();
            dbHelper.insert("STANDART",expression + result);
        }

    public void btnClick_delete(View view) {
        if (tv1.getText().equals("")) {
            tv1.setText(null);
        } else {
            int len = tv1.getText().length();
            String s = tv1.getText().toString();
            if (s.charAt(len - 1) == '.') {
                hasDot = false;
                tv1.setText(tv1.getText().subSequence(0, tv1.getText().length() - 1));

            } else {
                tv1.setText(tv1.getText().subSequence(0, tv1.getText().length() - 1));
            }
        }
    }

    public void btnClick_clear(View view) {
        tv1.setText("0");
        tv2.setText("");
        hasDot = false;
        isNew = true;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickMemoryBuffer(View view) {
        switch (view.getId()) {
            case R.id.buMS:
                tv3.setText("M");
                memory = Double.parseDouble(String.valueOf(tv1.getText()));
                break;
            case R.id.buMR:
                tv1.setText(String.valueOf(memory));
                break;
            case R.id.buMC:
                tv3.setText("");
                memory = 0;
                break;
            case R.id.buMPlus:
                memory += Double.parseDouble(String.valueOf(tv1.getText()));
                break;
            case R.id.buMMinus:
                memory -= Double.parseDouble(String.valueOf(tv1.getText()));
                break;
        }
    }

    private void saveText() {
        pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString(SAVED_TEXT1, tv1.getText().toString());
        ed.putString(SAVED_TEXT2, tv2.getText().toString());
        ed.commit();
    }

    private void loadText() {
        pref = getPreferences(MODE_PRIVATE);
        String savedText1 = pref.getString(SAVED_TEXT1, "");
        String savedText2 = pref.getString(SAVED_TEXT2, "");
        tv1.setText(savedText1);
        tv2.setText(savedText2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

}