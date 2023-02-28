package Entities;

import static java.lang.Integer.parseInt;

public class ComplexNum {
    float re, im;

    public ComplexNum(float re, float im) {
        this.re = re;
        this.im = im;
    }

    public void setRe(float re) {
        this.re = re;
    }

    public void setIm(float im) {
        this.im = im;
    }

    public float getRe() {
        return re;
    }

    public float getIm() {
        return im;
    }

    public void add(ComplexNum other) {
        this.re += other.getRe();
        this.im += other.getIm();
    }

    public void subtract(ComplexNum other) {
        this.re -= other.getRe();
        this.im -= other.getIm();
    }

    public void multiply(ComplexNum other) {
        //     (a.bi) * (c.di) ; a*c + adi + bci - bd;
        float tmpRe = this.getRe() * other.getRe() - this.getIm() * other.getIm();
        float tmpIm = this.getRe() * other.getIm() + this.getIm() * other.getRe();
        setRe(tmpRe);
        setIm(tmpIm);
    }

    public void divide(ComplexNum other) {
//      (a.bi) / (c.di)
        /**
         * ac + bd      +   bc - ad
         * _______          _______  *   i
         * c^2+d^2          c^2+d^2
         */
        float divisor = other.getRe() * other.getRe() + other.getIm() * other.getIm();
        float tmpRe = this.getRe() * other.getRe() + this.getIm() * other.getIm();
        float tmpIm = this.getIm() * other.getRe() - this.getRe() * other.getIm();
        tmpRe /= divisor;
        tmpIm /= divisor;
        setRe(tmpRe);
        setIm(tmpIm);

    }

    @Override
    public String toString() {
        return Integer.toString((int)getRe()) + (getIm()>0.0f? "+" : "") + Integer.toString((int)getIm()) + "*i";
    }
}
