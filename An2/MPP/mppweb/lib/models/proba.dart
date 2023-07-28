import 'categorievarsta.dart';
import 'entitate.dart';
import 'numeproba.dart';
import 'dart:convert';

class Proba extends Entitate<int> {
  NumeProba numeProba;
  CategorieVarsta categorieVarsta;

  Proba({required int id,required this.numeProba,required this.categorieVarsta}) : super(id: id);

  factory Proba.fromJson(Map<String, dynamic> json) {
    return Proba(
      id: json['id'],
      numeProba: NumeProba.fromJson(json['numeProba']),
      categorieVarsta: CategorieVarsta.fromJson(json['categorieVarsta']),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'numeProba': numeProba.toJson(),
    'categorieVarsta': categorieVarsta.toJson(),
  };
}
