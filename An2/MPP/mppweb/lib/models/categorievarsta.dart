import 'entitate.dart';
import 'dart:convert';


class CategorieVarsta extends Entitate<int> {
  int varstaMinima;
  int varstaMaxima;

  CategorieVarsta({required int id,required this.varstaMinima,required this.varstaMaxima}) : super(id: id);

  factory CategorieVarsta.fromJson(Map<String, dynamic> json) {
    return CategorieVarsta(
      id: json['id'],
      varstaMinima: json['varstaMinima'],
      varstaMaxima: json['varstaMaxima'],
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'varstaMinima': varstaMinima,
    'varstaMaxima': varstaMaxima,
  };
  String toString() {
    return varstaMinima.toString() + '-' + varstaMaxima.toString();
  }
}
