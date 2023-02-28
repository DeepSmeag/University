import Entities.ExpressionParser;

public class Main {
    public static void main(String[] args) {

        String[] thingies={"1+2*i","+","3-4*i","+","2+1*i","+","5-4*i"};
        ExpressionParser parser = new ExpressionParser(thingies);
        if(!parser.check()){
            System.out.println("The given expression is INVALID\n");
            return;
        }
        // if we get here it means the expression is valid
        // now we decompose it
        System.out.println(parser.parse());
// class item  = new class ceva
// Template
        /*
        clasa model, fie interfata, fie abstracta
        pe baza ei facem alte clase

        Factory(Addition) -> creeaza ComplexAddition
        Singleton - clasa de tip singleton se creeaza o singura data, apoi cand se creeaza obiecte de acel tip se returneaza instanta initiala
        deci de la a 2-a oara incolo este returnat obiectul deja existent, nu se creeaza altul
         */

    }
}