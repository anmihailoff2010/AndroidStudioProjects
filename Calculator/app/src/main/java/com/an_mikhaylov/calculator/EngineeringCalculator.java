package com.an_mikhaylov.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.an_mikhaylov.calculator.db.DBHelper;

public class EngineeringCalculator extends AppCompatActivity {
    private TextView tv1, tv2;
    private TextView tv3 = null;
    private boolean hasDot;
    private boolean isNew = true;
    private double memory = 0;
    private ActionBar actionBar;
    private String operator = "";
    private String number1;
    private SharedPreferences pref;
    final String SAVED_TEXT1 = "saved_text1";
    final String SAVED_TEXT2 = "saved_text2";
    private Button radDeg;
    private boolean rad_deg = false;
    private DBHelper dbHelper;
    private String expression = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineering_calculator);
        init();
        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setTitle(getString(R.string.engineering));
        }
        loadText();
    }

    private void init() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        hasDot = false;
        radDeg = findViewById(R.id.buRadDeg);
        dbHelper = new DBHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ordinaryCalculator) {
            Intent i = new Intent(EngineeringCalculator.this, StandartCalculator.class);
            startActivity(i);
        } else if (id == R.id.history) {
            Intent i = new Intent(EngineeringCalculator.this, HistoryActivity.class);
            i.putExtra("calcName", "ENGINEERING");
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
            case R.id.buSqrt:
                operator = "√";
                break;
            case R.id.buCbrt:
                operator = "∛";
                break;
            case R.id.buLog:
                operator = "log";
                break;
            case R.id.buLn:
                operator = "ln";
                break;
            case R.id.buFac:
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
            case R.id.buExp:
                operator = "exp";
                break;
            case R.id.bu10x:
                operator = "10^x";
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
            case "√":
                result = Math.sqrt(Double.parseDouble(number1));
                tv2.setText("√" + number1 + " = ");
                tv1.setText(result + "");
                break;
            case "∛":
                result = Math.cbrt(Double.parseDouble(number1));
                tv2.setText("∛" + number1 + " = ");
                tv1.setText(result + "");
                break;
            case "log":
                result = Math.log10(Double.parseDouble(number1));
                tv2.setText("log" + number1 + " = ");
                tv1.setText(result + "");
                break;
            case "ln":
                result = Math.log(Double.parseDouble(number1));
                tv2.setText("ln" + number1 + " = ");
                tv1.setText(result + "");
                break;
            case "x!":
                result = Double.parseDouble(number1);
                int i = Integer.parseInt(number1) - 1;
                while (i > 0) {
                    result = result * i;
                    i--;
                }
                tv2.setText(number1 + "!" + " = ");
                tv1.setText(result + "");
                expression = tv2.getText().toString();
                break;
            case "exp":
                result = Math.exp(Double.parseDouble(number1));
                tv2.setText("exp" + number1 + " = ");
                tv1.setText(result + "");
                break;
            case "10^x":
                result = Math.pow(10, Double.parseDouble(number1));
                tv2.setText("10^" + number1 + " = ");
                tv1.setText(result + "");
                break;
            case "e":
                result = Math.E;
                tv2.setText("e" + " = ");
                tv1.setText(result + "");
                break;
            case "Pi":
                result = Math.PI;
                tv2.setText("pi" + " = ");
                tv1.setText(result + "");
                break;
            case "1/X":
                String number = tv1.getText().toString();
                if (Double.parseDouble(number) == 0) {
                    tv1.setText("Делитель не может быть нулевым!");
                } else {
                    result = 1 / Double.parseDouble(number);
                    tv2.setText("1/" + number + " = ");
                    tv1.setText(result + "");
                }
                break;
            case "sin":
                if (!rad_deg) {
                    result = Math.sin(Math.toRadians(Double.parseDouble(number1)));
                    tv2.setText("sin" + number1 + " = ");
                    tv1.setText(result + "");
                } else {
                    result = Math.sin(Double.parseDouble(number1));
                    tv2.setText("sin" + number1 + " = ");
                    tv1.setText(result + "");
                }
                break;
            case "cos":
                if (!rad_deg) {
                    result = Math.cos(Math.toRadians(Double.parseDouble(number1)));
                    tv2.setText("cos" + number1 + " = ");
                    tv1.setText(result + "");
                } else {
                    result = Math.cos(Double.parseDouble(number1));
                    tv2.setText("cos" + number1 + " = ");
                    tv1.setText(result + "");
                }
                break;
            case "tan":
                if (!rad_deg) {
                    result = Math.tan(Math.toRadians(Double.parseDouble(number1)));
                    tv2.setText("tan" + number1 + " = ");
                    tv1.setText(result + "");
                } else {
                    result = Math.tan(Double.parseDouble(number1));
                    tv2.setText("tan" + number1 + " = ");
                    tv1.setText(result + "");
                }
                break;
        }
        expression = tv2.getText().toString();
        dbHelper.insert("ENGINEERING", expression + result);
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

    public void onClickPercent(View view) {
        if (operator == "") {
            String number = tv1.getText().toString();
            double temp = Double.parseDouble(number) / 100;
            tv2.setText(number + "%" + " = ");
            tv1.setText(temp + "");
            expression = tv2.getText().toString();
            dbHelper.insert("ENGINEERING", expression + temp);
        } else {
            Double result = 0.0;
            String number2 = tv1.getText().toString();
            switch (operator) {
                case "✕":
                    result = Double.parseDouble(number1) * Double.parseDouble(number2) / 100;
                    tv2.setText(number1 + " ✕ " + number2 + "%" + " = ");
                    break;
                case "÷":
                    result = Double.parseDouble(number1) / Double.parseDouble(number2) * 100;
                    tv2.setText(number1 + " ÷ " + number2 + "%" + " = ");
                    break;
                case "+":
                    result = Double.parseDouble(number1) + Double.parseDouble(number1) * Double.parseDouble(number2) / 100;
                    tv2.setText(number1 + " + " + number2 + "%" + " = ");
                    break;
                case "-":
                    result = Double.parseDouble(number1) - Double.parseDouble(number1) * Double.parseDouble(number2) / 100;
                    tv2.setText(number1 + " - " + number2 + "%" + " = ");
                    break;
            }
            tv1.setText(result + "");
            operator = "";
            expression = tv2.getText().toString();
            dbHelper.insert("ENGINEERING", expression + result);
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

    public void btnRadDeg(View view) {
        if (!rad_deg) {
            rad_deg = true;
            radDeg.setText("Deg");
            tv3.setText("Rad");
        } else {
            rad_deg = false;
            radDeg.setText("Rad");
            tv3.setText("Deg");
        }
    }
}


