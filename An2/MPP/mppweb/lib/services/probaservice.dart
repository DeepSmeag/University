import 'dart:convert';
import 'package:http/http.dart' as http;


import '../models/proba.dart';

class ProbaService {
  static const String _baseUrl = 'http://localhost:8080/proba';

  // GET
  static Future<List<Proba>> getProbe() async {
    final response = await http.get(Uri.parse(_baseUrl));

    if (response.statusCode == 200) {
      Iterable list = json.decode(response.body);
      return list.map((proba) => Proba.fromJson(proba)).toList();
    } else {
      throw Exception('Failed to load probe');
    }
  }

  // POST
  static Future<Proba> createProba(Proba proba) async {
    final response = await http.post(
      Uri.parse(_baseUrl),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: json.encode(proba.toJson()),
    );


    if (response.statusCode == 200) {
      return Proba.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to create proba');
    }
  }

  // PUT
  static Future<String> updateProba(Proba proba) async {
    final response = await http.put(
      Uri.parse('$_baseUrl/${proba.id}'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: json.encode(proba.toJson()),
    );
  print(response.body);
    if (response.statusCode == 200) {
      // return Proba.fromJson(json.decode(response.body));
      return response.body;
    } else {
      throw Exception('Failed to update proba');
    }
  }

  // DELETE
  static Future<void> deleteProba(int id) async {
    final response = await http.delete(
      Uri.parse('$_baseUrl/$id'),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to delete proba');
    }
  }
}
