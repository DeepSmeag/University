import streamlit as st
import requests


st.set_page_config(layout="wide")
col1, col2, col3 = st.columns(3)
with col2:
    st.title("Word Similarity Checker")

    word1 = st.text_input("Enter first word")
    word2 = st.text_input("Enter second word")

    if st.button("Calculate Similarity"):
        if word1 and word2:
            response = requests.post("http://127.0.0.1:8001/similarity/", json={"word1": word1, "word2": word2})
            similarities = response.json()
            st.write("Similarities:")
            for method, score in similarities.items():
                st.write(f"## {method}: {score}")
        else:
            st.write("Please enter both words.")
with col3:
    st.write("## Words extracted for Cosine similarity:")
    response = requests.get("http://127.0.0.1:8001/worddict/")
    if response.status_code == 200:
        worddict = response.json()
        st.write(worddict)
    else:
        st.write("Failed to fetch")


# run with "streamlit run frontend.py"