from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel
import numpy as np
import rowordnet as rwn
from deep_translator import GoogleTranslator
from nltk.corpus import wordnet_ic, wordnet

app = FastAPI()
rown = rwn.RoWordNet()
ic = wordnet_ic.ic('ic-bnc-resnik-add1.dat')

def buildDictionary():
    worddict = {}
    with open("selectieCorola.txt", "r") as readFile:
        for line in readFile:
            parts = line.split()
            worddict[parts[0]] = [float(x) for x in parts[1:]]
    return worddict
worddict = buildDictionary()

class Words(BaseModel):
    word1: str
    word2: str

def dummy_similarity(word1: str, word2: str) -> float:
    # Placeholder similarity calculation
    return 0.5

def cosineSimilarity(word1: str, word2: str) -> float:
    try:
        vec1 = worddict[word1]
        vec2 = worddict[word2]
    # Calculate cosine similarity
        return np.dot(vec1, vec2) / (np.linalg.norm(vec1) * np.linalg.norm(vec2))
    except:
        return -1
def translation(word: str) -> str:
    try:
        tr = GoogleTranslator(source='ro', target='en').translate(word)
        print(tr)
        if len(tr.split()) == 2:
            return tr.split()[1]
        return tr
    except:
        return "Translation not found"
@app.get("/worddict/")
async def getWordDict():
    return JSONResponse(content = worddict)
@app.post("/similarity/")
async def calculate_similarity(words: Words):
    # methods = {"dummy_method": dummy_similarity(words.word1, words.word2)}
    methods = {}
    try :
        synset1 = rown.synsets(words.word1)[0]
        synset2 = rown.synsets(words.word2)[0]
        methods['Path Similarity'] = rown.path_similarity(synset1,synset2),
        methods['LCH Similarity'] = rown.lch_similarity(synset1,synset2),
        methods['WUP Similarity'] = rown.wup_similarity(synset1,synset2),
    except:
        methods['Path Similarity'] = "Words not found"
        methods['LCH Similarity'] = "Words not found"
        methods['WUP Similarity'] = "Words not found"
    try:
        tr1 = translation(words.word1)
        tr2 = translation(words.word2)
        synset1 = wordnet.synsets(tr1)[0]
        synset2 = wordnet.synsets(tr2)[0]
        methods['Res Translation'] = wordnet.res_similarity(synset1, synset2, ic)
        methods['JCN Translation'] = wordnet.jcn_similarity(synset1, synset2, ic)
        methods['LIN Translation'] = wordnet.lin_similarity(synset1, synset2, ic)
        if tr1 == tr2:
            methods['JCN Translation'] = 1
    except:
        methods['Res Translation'] = "Words not found"
        methods['JCN Translation'] = "Words not found"
        methods['LIN Translation'] = "Words not found"
    cosineSim = cosineSimilarity(words.word1, words.word2)
    if cosineSim == -1:
        methods['Cosine Similarity'] = "Words not found"
    else:
        methods["Cosine Similarity"] = cosineSim
    return methods

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8001)
# run with "python backend.py"