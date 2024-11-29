import React, { useState } from "react";
import "../styles/chatbot.css";

const Chatbot = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState("");

  // Pre-defined Questions
  const preMadeQuestions = [
    {
      text: "Who is CPS?",
      response:
        "Canadian Postal Service (CPS) is a shipping company that delivers packages across Canada with reliability and affordability. Established in 1955, CPS is known for its efficient services.",
    },
    {
      text: "What are the normal delivery times for my package?",
      response:
        "Normal delivery takes 3 business days, and expedited delivery takes 2 business days.",
    },
    {
      text: "How do I track my delivery?",
      response:
        "To track your package, enter the provided tracking code into the 'Track your Order' box on the homepage.",
    },
    {
      text: "What services does CPS offer?",
      response:
        "CPS offers station drop-offs at locations across Canada and home pickup services. Fill out the online form for home pickup, and a driver will collect your package.",
    },
  ];

  // Toggle Chat Window
  const toggleChat = () => {
    setIsOpen(!isOpen);
  };

  // Send Message Logic
  const sendMessage = async (message) => {
    const userMessage = message || userInput;

    if (!userMessage.trim()) return;

    // Add user message to the chat
    setMessages([...messages, { type: "user", text: `You: ${userMessage}` }]);

    // Check for pre-made question response
    const preMadeResponse = preMadeQuestions.find(
      (q) => q.text.toLowerCase() === userMessage.toLowerCase()
    );

    if (preMadeResponse) {
      // Add pre-made response to chat
      setMessages((prev) => [
        ...prev,
        { type: "bot", text: `Assistant: ${preMadeResponse.response}` },
      ]);
      setUserInput("");
      return;
    }

    // Otherwise, send the user message to the chatbot API
    setUserInput("");
    try {
      const response = await fetch("http://localhost:8080/api/chat", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: userMessage }),
      });

      const data = await response.json();

      // Add bot response to the chat
      setMessages((prev) => [
        ...prev,
        { type: "bot", text: `Assistant: ${data.assistant}` },
      ]);
    } catch (error) {
      console.error("Error:", error);
      setMessages((prev) => [
        ...prev,
        { type: "bot", text: "Assistant: Sorry, something went wrong." },
      ]);
    }
  };

  return (
    <div className="chatbot">
      {/* Chat Icon */}
      <div className="chat-icon" onClick={toggleChat}>
        💬
      </div>

      {/* Chat Window */}
      {isOpen && (
        <div className="chat-window">
          <div className="chat-header">
            <span>Chatbot</span>
            <button onClick={toggleChat}>X</button>
          </div>

          {/* Pre-made Questions */}
          <div className="pre-made-questions">
            {preMadeQuestions.map((q, idx) => (
              <div
                key={idx}
                className="pre-made-question"
                onClick={() => sendMessage(q.text)}
              >
                {q.text}
              </div>
            ))}
          </div>

          {/* Chat Messages */}
          <div className="chat-messages">
            {messages.map((msg, idx) => (
              <div
                key={idx}
                className={`message ${msg.type === "user" ? "user" : "bot"}`}
              >
                {msg.text}
              </div>
            ))}
          </div>

          {/* Input Box */}
          <div className="chat-input">
            <input
              type="text"
              value={userInput}
              onChange={(e) => setUserInput(e.target.value)}
              placeholder="Type a message..."
            />
            <button onClick={() => sendMessage()}>Send</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatbot;
