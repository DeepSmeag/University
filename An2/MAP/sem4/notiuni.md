### serializare (Serializable)
- transformation of a java object into a constant stream of bytes that can be safely transferred
over to a database or anything of the sort; transforming something abstract into something concrete that we can send somewhere
- classes that can be serialized need to implement the interface *Serializable*
- objects can be serialized on one platform and deserialized on another platform
- static variables are not serializable, they don't get transferred together with the rest of the members
- we can also use the kw "transient" to ignore those variables
### serialVersionUID
- a long number that is associated with an instance of that object; it's used to verify whether after deserialization we have the same object

### getResource
- used to return a URL object to the resource we give as a parameter
- object can be further used in whatever way necesary

### FileReader
- predefined class used to read files
### readAllLines
- a function to read all lines of a file; uses a BufferedReader behind the scenes
- returns a list of strings

 

### ObjectOutputStream
- encodes java objects and writes them to a stream
- objects must be serializable
- primitives can be written by default
- decoding is done using ObjectInputStream

### writeObject
- writes the specified object to ObjectOutputStream
