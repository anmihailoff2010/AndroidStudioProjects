package com.an_mikhaylov.engineeringcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editText2;
    private EditText memoryBuffer = null;
    private Boolean isNew = true;
    private String oldNumber;
    private String operator = "";
    private double memory = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        memoryBuffer = findViewById(R.id.memoryBuffer);
    }

    public void onClickNumber(View view) {
        if (isNew)
            editText.setText("");
        isNew = false;
        String number = editText.getText().toString();
        switch (view.getId()) {
            case R.id.bu0:
                number = number + "0";
                break;
            case R.id.bu1:
                number = number + "1";
                break;
            case R.id.bu2:
                number = number + "2";
                break;
            case R.id.bu3:
                number = number + "3";
                break;
            case R.id.bu4:
                number = number + "4";
                break;
            case R.id.bu5:
                number = number + "5";
                break;
            case R.id.bu6:
                number = number + "6";
                break;
            case R.id.bu7:
                number = number + "7";
                break;
            case R.id.bu8:
                number = number + "8";
                break;
            case R.id.bu9:
                number = number + "9";
                break;
            case R.id.buDecimalPoint:
                if (decimalPointIsPresent(number)) {
                } else {
                    number = number + ".";
                }
                break;
//            case R.id.buPlusMinus:
//                if (numberIsZero(number)) {
//                    number = "0";
//                } else {
//                    if (minusIsPresent(number)) {
//                        number = number.substring(1);
//                    } else {
//                        number = "-" + number;
//                    }
//                }
//                break;
        }
        editText.setText(number);
    }

    private boolean numberIsZero(String number) {
        if(number.equals("0") || number.equals("")) {
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
        oldNumber = editText.getText().toString();
        switch (view.getId()) {
            case R.id.buMultiplication:
                operator = "✕";
                break;
            case R.id.buDel:
                operator = "÷";
                break;
            case R.id.buPlus:
                operator = "+";
                break;
            case R.id.buMinus:
                operator = "-";
                break;
            case R.id.buXy:
                operator = "^";
                break;
        }
    }

    public void onClickEqual(View view) {
        String newNumber = editText.getText().toString();
        Double result = 0.0;
        switch (operator) {
            case "✕":
                result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber);
                break;
            case "÷":
                if (Double.parseDouble(newNumber) == 0) {
                    Toast.makeText(this, "На ноль делить нельзя!!!", Toast.LENGTH_SHORT).show();
                } else {
                    result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber);
                }
                break;
            case "+":
                result = Double.parseDouble(oldNumber) + Double.parseDouble(newNumber);
                break;
            case "-":
                result = Double.parseDouble(oldNumber) - Double.parseDouble(newNumber);
                break;
            case "^":
                result = Math.pow(Double.parseDouble(oldNumber),Double.parseDouble(newNumber));


                break;
        }
        editText2.setText(oldNumber + " " + operator + " " + newNumber + " =");
        editText.setText(result + "");
    }

    public void onClickC(View view) {
        editText.setText("0");
        editText2.setText("");
        isNew = true;
    }

    public boolean decimalPointIsPresent(String number) {
        if (number.indexOf(".") == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void onClickMemoryBuffer(View view) {
        switch (view.getId()) {
            case R.id.buMS:
                memoryBuffer.setText("M");
                memory = Double.valueOf(String.valueOf(editText.getText()));
                break;
            case R.id.buMR:
                editText.setText(String.valueOf(memory));
                break;
            case R.id.buMC:
                memoryBuffer.setText("");
                memory = 0;
                break;
            case R.id.buMPlus:
                memory += Double.valueOf(String.valueOf(editText.getText()));
                break;
            case R.id.buMMinus:
                memory -= Double.valueOf(String.valueOf(editText.getText()));
                break;
        }
    }

    public void onClickExponentiation(View view) {
        String number = editText.getText().toString();
        Double result = 0.0;
        switch (view.getId()) {
            case R.id.buSquaring:
                result = Math.pow(Double.parseDouble(number), 2);
                editText2.setText(number + " ^ 2" + " =");
                break;
            case R.id.buCubing:
                result = Math.pow(Double.parseDouble(number), 3);
                editText2.setText(number + " ^ 3" + " =");
                break;
        }
        editText.setText(result + "");
    }

//    public void onClickPercent(View view) {
//        if (operator == "") {
//            String number = editText.getText().toString();
//            double temp = Double.parseDouble(number) / 100;
//            number = temp + "";
//            editText.setText(number);
//        } else {
//            Double result = 0.0;
//            String newNumber = editText.getText().toString();
//
//            switch (operator) {
//                case "✕":
//                    result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber) / 100;
//                    break;
//                case "÷":
//                    result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber) * 100;
//                    break;
//                case "+":
//                    result = Double.parseDouble(oldNumber) + Double.parseDouble(oldNumber) * Double.parseDouble(newNumber) / 100;
//                    break;
//                case "-":
//                    result = Double.parseDouble(oldNumber) - Double.parseDouble(oldNumber) * Double.parseDouble(newNumber) / 100;
//                    break;
//            }
//            editText.setText(result + "");
//            operator = "";
//        }
//    }
}