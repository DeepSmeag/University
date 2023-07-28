import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:mppweb/services/probaservice.dart';

import 'models/categorievarsta.dart';
import 'models/numeproba.dart';
import 'models/proba.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter REST API CRUD',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(
          title: 'Flutter REST API CRUD Home Page', key: const Key("value")),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({required Key key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late Proba _proba;
  late Map probeList;
  List<Proba> probe = [];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child:
            Column(mainAxisAlignment: MainAxisAlignment.spaceEvenly, children: <
                Widget>[
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: () async {
              // Testing GET
              probe = await ProbaService.getProbe();
              setState(() {});
              print('GET response: ${probe.map((proba) => proba.toJson())}');
              // for (var proba in probe) {
              //   probeList.addAll(proba.toJson());
              // }
              // print(probeList);
            },
            child: Text('Test GET'),
          ),
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: () async {
              // Testing POST
              Proba newProba = Proba(
                id: 0,
                numeProba: NumeProba(numeProba: "REST", id: 4),
                categorieVarsta:
                    CategorieVarsta(id: 1, varstaMinima: 6, varstaMaxima: 8),
              );

              try {
                _proba = await ProbaService.createProba(newProba);
                // clear probe and add only _proba to it
                probe.clear();
                probe.add(_proba);
                setState(() {});
                print('POST response: ${_proba.toJson()}');
              } catch (e) {
                print('Failed to create proba: $e');
              }
            },
            child: Text('Test POST'),
          ),
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: () async {
              // Testing PUT
              if (_proba != null) {
                Proba updatedProba = Proba(
                  id: _proba.id,
                  numeProba: NumeProba(numeProba: "REST", id: 4),
                  categorieVarsta:
                      CategorieVarsta(id: 2, varstaMinima: 9, varstaMaxima: 11),
                );

                try {
                  String result = await ProbaService.updateProba(updatedProba);
                  //create proba from result
                  _proba = Proba.fromJson(jsonDecode(result));
                  probe.clear();
                  probe.add(_proba);
                  setState(() {});
                  print('PUT response: ' + result);
                } catch (e) {
                  print('Failed to update proba: $e');
                }
              } else {
                print('No proba to update');
              }
            },
            child: Text('Test PUT'),
          ),
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: () async {
              // Testing DELETE
              if (_proba != null) {
                try {
                  await ProbaService.deleteProba(_proba.id);
                  print('DELETE successful');
                  probe.clear();
                  setState(() {});
                  // alert popup to confirm successful delete
                  showDialog(
                    context: context,
                    builder: (BuildContext context) {
                      return AlertDialog(
                        title: Text('Success'),
                        content: Text('Successful delete'),
                        actions: <Widget>[
                          TextButton(
                            child: Text('OK'),
                            onPressed: () {
                              Navigator.of(context).pop();
                            },
                          ),
                        ],
                      );
                    },
                  );
                  // _proba = null;
                } catch (e) {
                  print('Failed to delete proba: $e');
                }
              } else {
                print('No proba to delete');
              }
            },
            child: Text('Test DELETE'),
          ),
          Expanded(
            child: SingleChildScrollView(
              scrollDirection: Axis.vertical,
              child: DataTable(
                columns: const <DataColumn>[
                  DataColumn(
                    label: Text('ID'),
                  ),
                  DataColumn(
                    label: Text('Nume Proba'),
                  ),
                  DataColumn(
                    label: Text('Categorie Varsta'),
                  ),
                ],
                rows: probe
                    .map((e) => DataRow(cells: [
                          DataCell(Text(e.id.toString())),
                          DataCell(Text(e.numeProba.toString())),
                          DataCell(Text(e.categorieVarsta.toString())),
                        ]))
                    .toList(),
              ),
            ),
          ),
        ]),
      ),
    );
  }
}
