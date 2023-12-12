import { useState } from "react";
import "./App.css";

function App() {
  const [message, setMessage] = useState("");
  const [suggestions, setSuggestions] = useState({});

  const handleSubmission = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        `http://localhost:8080/api/parse-text/${encodeURIComponent(
          message.trim()
        )}`
      );

      if (response.ok) {
        const data = await response.json();
        setSuggestions(data);
      } else {
        console.error(
          "Erro ao processar texto:",
          response.status,
          response.statusText
        );
        alert("Erro ao processar texto");
      }
    } catch (error) {
      console.error("Erro ao processar texto:", error.message);
      alert("Erro ao processar texto");
    }
  };

  return (
    <div className="container">
      <h1>Sugestão Ortográfica</h1>

      <form onSubmit={handleSubmission} className="form">
        <h2>Escreva um texto:</h2>
        <textarea
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          rows={10}
          cols={70}
        />
        <button type="submit">Sugerir correções</button>
      </form>

      <div className="text_container">
        <h2>Sugestões de Correção:</h2>
        <div className="text">
          {message.split(" ").map((word, index) => {
            const suggestion = suggestions[word];

            if (!suggestion) return <span key={index}>{word} </span>;

            return (
              <span
                title={suggestion.toString()}
                style={{ textDecoration: "underline", color: "red" }}
                key={index}
              >
                {word}{" "}
              </span>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default App;
