from fastapi import FastAPI
from fastapi.responses import JSONResponse

from pydantic import BaseModel

app = FastAPI()

class InputToValidate(BaseModel):
    inputText: str

def helperFunction(params):
    print("Helper function, only visible to the backend")


@app.get("/endpoint/")
async def doSomething():
    return JSONResponse(content={"message": "Hello from the backend!"})
@app.post("/anotherendpoint/"):
async def doSomethingElse():
    dictionary = {"message": "Hello from the backend!"}
    return dictionary

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app.host="127.0.0.1", port=8001)