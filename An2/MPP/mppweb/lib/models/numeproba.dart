import 'entitate.dart';
import 'dart:convert';

class NumeProba extends Entitate<int> {
  String numeProba;

  NumeProba({required int id,required this.numeProba}) : super(id: id);

  factory NumeProba.fromJson(Map<String, dynamic> json) {
    return NumeProba(
      id: json['id'],
      numeProba: json['numeProba'],
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'numeProba': numeProba,
  };

  String toString() {
    return numeProba;
  }
}
