package com.an_mikhaylov.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView previousCalculation;
    private TextView display;
    private TextView memoryBuffer = null;
    private boolean isNew = true;
    private double memory = 0;
    //private ActionBar actionBar;
    private String operator = "";
    private String number1;
    private SharedPreferences pref;
    final String SAVED_TEXT1 = "saved_text1";
    final String SAVED_TEXT2 = "saved_text2";
    private Button radDeg;
    private boolean rad_deg = false;
    //private DBHelper dbHelper;
    private String expression = "";
    private boolean hasDot;

    private Stack<Character> operatorStack;
    private Stack<Double> valueStack;
    private boolean error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadText();
    }

    private void init() {
        previousCalculation = findViewById(R.id.previousCalculationView);
        memoryBuffer = findViewById(R.id.memoryBuffer);
        display = findViewById(R.id.displayText);
        radDeg = findViewById(R.id.buRadDeg);
        //dbHelper = new DBHelper(this);
        operatorStack = new Stack<Character>();
        valueStack = new Stack<Double>();
        error = false;
    }

    public void btnClick_number(View view) {
        if (isNew)
            display.setText("");
        isNew = false;
        String number = display.getText().toString();
        switch (view.getId()) {
            case R.id.buZero:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = "0";
                } else {
                    number = number + "0";
                }
                break;
            case R.id.buOne:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "1";
                break;
            case R.id.buTwo:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "2";
                break;
            case R.id.buThree:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "3";
                break;
            case R.id.buFour:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "4";
                break;
            case R.id.buFive:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "5";
                break;
            case R.id.buSix:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "6";
                break;
            case R.id.buSeven:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "7";
                break;
            case R.id.buEight:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "8";
                break;
            case R.id.buNine:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "9";
                break;
            case R.id.buDecimal:
                if (decimalPointIsPresent(number)) {
                }
                else if (zeroIsFirst(number)) {
                    number = "0.";
                } else {
                    number = number + ".";
                }
                break;
            case R.id.buPlusMinus:
                if (numberIsZero(number)) {
                    number = "0";
                } else {
                    if (minusIsPresent(number)) {
                        number = number.substring(1);
                    } else {
                        number = "-" + number;
                    }
                }
                break;
        }
        display.setText(number);
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

    private boolean numberIsZero(String number) {
        if (number.equals("0") || number.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean minusIsPresent(String number) {
        if (number.charAt(0) == '-') {
            return true;
        } else {
            return false;
        }
    }

    public void onClickOperation(View view) {
        isNew = true;
        number1 = display.getText().toString();
        switch (view.getId()) {
            case R.id.buMultiply:
                operator = "✕";
                break;
            case R.id.buDivide:
                operator = "÷";
                break;
            case R.id.buSubtract:
                operator = "-";
                break;
            case R.id.buAdd:
                operator = "+";
                break;
            case R.id.buXsquared:
                operator = "x²";
                break;
            case R.id.buXcubed:
                operator = "x³";
                break;
            case R.id.buXpowerY:
                operator = "xⁿ";
                break;
            case R.id.buSquareRoot:
                operator = "√";
                break;
            case R.id.buRootOfN:
                operator = "@string/yRootText";
                break;
            case R.id.buLog:
                operator = "log";
                break;
            case R.id.buLn:
                operator = "ln";
                break;
            case R.id.buFactorial:
                operator = "x!";
                break;
            case R.id.buSin:
                operator = "sin";
                break;
            case R.id.buCos:
                operator = "cos";
                break;
            case R.id.buTan:
                operator = "tan";
                break;
            case R.id.buE:
                operator = "e";
                break;
            case R.id.buPi:
                operator = "Pi";
                break;
            case R.id.bu1delX:
                operator = "1/X";
                break;
        }
    }

    public void onClickEqual(View view) {
        String number2 = display.getText().toString();
        Double result = 0.0;
        if (display.getText().equals("")) {
        } else {
            switch (operator) {
                case "✕":
                    result = Double.parseDouble(number1) * Double.parseDouble(number2);
                    previousCalculation.setText(number1 + " " + operator + " " + number2 + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "÷":
                    if (Double.parseDouble(number2) == 0) {
                        display.setText("На ноль делить нельзя!!!");
                    } else {
                        result = Double.parseDouble(number1) / Double.parseDouble(number2);
                        previousCalculation.setText(number1 + " " + operator + " " + number2 + " = ");
                        display.setText(result + "");
                    }
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "+":
                    result = Double.parseDouble(number1) + Double.parseDouble(number2);
                    BigDecimal bd = new BigDecimal(Double.toString(result));
                    bd = bd.setScale(1, RoundingMode.HALF_UP);
                    previousCalculation.setText(number1 + " " + operator + " " + number2 + " = ");
                    display.setText(bd + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + bd);
                    break;
                case "-":
                    result = Double.parseDouble(number1) - Double.parseDouble(number2);
                    bd = new BigDecimal(Double.toString(result));
                    bd = bd.setScale(1, RoundingMode.HALF_UP);
                    previousCalculation.setText(number1 + " " + operator + " " + number2 + " = ");
                    display.setText(bd + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + bd);
                    break;
                case "x²":
                    result = Math.pow(Double.parseDouble(number1), 2);
                    previousCalculation.setText(number1 + " ^ 2" + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "x³":
                    result = Math.pow(Double.parseDouble(number1), 3);
                    previousCalculation.setText(number1 + " ^ 3" + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "xⁿ":
                    result = Math.pow(Double.parseDouble(number1), Double.parseDouble(number2));
                    previousCalculation.setText(number1 + " ^ " + number2 + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "√":
                    result = Math.sqrt(Double.parseDouble(number1));
                    previousCalculation.setText("√" + number1 + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "@string/yRootText":
                    result = Math.pow(Double.parseDouble(number1), 1 / Double.parseDouble(number2));
                    previousCalculation.setText(number2 + "√" + number1 + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "log":
                    result = Math.log10(Double.parseDouble(number1));
                    previousCalculation.setText("log" + number1 + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "ln":
                    result = Math.log(Double.parseDouble(number1));
                    previousCalculation.setText("ln" + number1 + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "x!":
                    result = Double.parseDouble(number1);
                    int i = Integer.parseInt(number1) - 1;
                    while (i > 0) {
                        result = result * i;
                        i--;
                    }
                    previousCalculation.setText(number1 + "!" + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "e":
                    result = Math.E;
                    previousCalculation.setText("e" + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "Pi":
                    result = Math.PI;
                    previousCalculation.setText("pi" + " = ");
                    display.setText(result + "");
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "1/X":
                    String number = display.getText().toString();
                    if (Double.parseDouble(number) == 0) {
                        display.setText("Делитель не может быть нулевым!");
                    } else {
                        result = 1 / Double.parseDouble(number);
                        previousCalculation.setText("1/" + number + " = ");
                        display.setText(result + "");
                    }
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + result);
                    break;
                case "sin":
                    if (!rad_deg) {
                        result = Math.sin(Math.toRadians(Double.parseDouble(number1)));
                        bd = new BigDecimal(Double.toString(result));
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        previousCalculation.setText("sin(" + number1 + ") = ");
                        display.setText(bd + "");
                    } else {
                        result = Math.sin(Double.parseDouble(number1));
                        bd = new BigDecimal(Double.toString(result));
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        previousCalculation.setText("sin(" + number1 + ") = ");
                        display.setText(bd + "");
                    }
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + bd);
                    break;
                case "cos":
                    if (!rad_deg) {
                        result = Math.cos(Math.toRadians(Double.parseDouble(number1)));
                        bd = new BigDecimal(Double.toString(result));
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        previousCalculation.setText("cos(" + number1 + ") = ");
                        display.setText(bd + "");
                    } else {
                        result = Math.cos(Double.parseDouble(number1));
                        bd = new BigDecimal(Double.toString(result));
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        previousCalculation.setText("cos(" + number1 + ") = ");
                        display.setText(bd + "");
                    }
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + bd);
                    break;
                case "tan":
                    if (!rad_deg) {
                        result = Math.tan(Math.toRadians(Double.parseDouble(number1)));
                        bd = new BigDecimal(Double.toString(result));
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        previousCalculation.setText("tan(" + number1 + ") = ");
                        display.setText(bd + "");
                    } else {
                        result = Math.tan(Double.parseDouble(number1));
                        bd = new BigDecimal(Double.toString(result));
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        previousCalculation.setText("tan(" + number1 + ") = ");
                        display.setText(bd + "");
                    }
                    expression = previousCalculation.getText().toString();
                    //dbHelper.insert("ENGINEERING", expression + bd);
                    break;
            }
        }
    }

    public void clearBTNPush(View view){
        display.setText("0");
        previousCalculation.setText("");
        hasDot = false;
        isNew = true;
    }

    public void backspaceBTNPush(View view) {
        if (display.getText().equals("")) {
            display.setText(null);
        } else {
            int len = display.getText().length();
            String s = display.getText().toString();
            if (s.charAt(len - 1) == '.') {
                hasDot = false;
                display.setText(display.getText().subSequence(0, display.getText().length() - 1));
            } else {
                display.setText(display.getText().subSequence(0, display.getText().length() - 1));
            }
        }
    }

    public void onClickPercent(View view) {
        if (operator == "") {
            String number = display.getText().toString();
            double temp = Double.parseDouble(number) / 100;
            previousCalculation.setText(number + "%" + " = ");
            display.setText(temp + "");
            expression = previousCalculation.getText().toString();
            //dbHelper.insert("ENGINEERING", expression + temp);
        } else {
            Double result = 0.0;
            String number2 = display.getText().toString();
            switch (operator) {
                case "✕":
                    result = Double.parseDouble(number1) * Double.parseDouble(number2) / 100;
                    previousCalculation.setText(number1 + " ✕ " + number2 + "%" + " = ");
                    break;
                case "÷":
                    result = Double.parseDouble(number1) / Double.parseDouble(number2) * 100;
                    previousCalculation.setText(number1 + " ÷ " + number2 + "%" + " = ");
                    break;
                case "+":
                    result = Double.parseDouble(number1) + Double.parseDouble(number1) * Double.parseDouble(number2) / 100;
                    previousCalculation.setText(number1 + " + " + number2 + "%" + " = ");
                    break;
                case "-":
                    result = Double.parseDouble(number1) - Double.parseDouble(number1) * Double.parseDouble(number2) / 100;
                    previousCalculation.setText(number1 + " - " + number2 + "%" + " = ");
                    break;
            }
            display.setText(result + "");
            operator = "";
            expression = previousCalculation.getText().toString();
            //dbHelper.insert("ENGINEERING", expression + result);
        }
    }

    public void onClickMemoryBuffer(View view) {
        switch (view.getId()) {
            case R.id.buMS:
                if (display.getText().equals("")) {
                } else {
                    memoryBuffer.setText("M");
                    memory = Double.parseDouble(String.valueOf(display.getText()));
                }
                break;
            case R.id.buMR:
                display.setText(String.valueOf(memory));
                break;
            case R.id.buMC:
                memoryBuffer.setText("");
                memory = 0;
                break;
            case R.id.buMPlus:
                if (display.getText().equals("")) {
                } else {
                    memory += Double.parseDouble(String.valueOf(display.getText()));
                }
                break;
            case R.id.buMMinus:
                if (display.getText().equals("")) {
                } else {
                    memory -= Double.parseDouble(String.valueOf(display.getText()));
                }
                break;
        }
    }

    private void saveText() {
        pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString(SAVED_TEXT1, display.getText().toString());
        ed.putString(SAVED_TEXT2, previousCalculation.getText().toString());
        ed.commit();
    }

    private void loadText() {
        pref = getPreferences(MODE_PRIVATE);
        String savedText1 = pref.getString(SAVED_TEXT1, "");
        String savedText2 = pref.getString(SAVED_TEXT2, "");
        display.setText(savedText1);
        previousCalculation.setText(savedText2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    public void btnRadDeg(View view) {
        if (!rad_deg) {
            rad_deg = true;
            radDeg.setText("Deg");
            memoryBuffer.setText("Rad");
        } else {
            rad_deg = false;
            radDeg.setText("Rad");
            memoryBuffer.setText("Deg");
        }
    }

    public void parOpenBTNPush(View view) {
        display.setText("(");
    }

    public void parCloseBTNPush(View view) {
        display.setText(")");
    }


}