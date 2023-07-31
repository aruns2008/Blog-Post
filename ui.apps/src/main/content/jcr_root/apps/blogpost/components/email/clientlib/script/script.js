const urlParams = new URLSearchParams(window.location.search);
const emailSent = urlParams.get("emailSent");
const emailSending = document.querySelector(".emailbox");
const emailSuccess = document.querySelector(".emailbox__success");

// Display the appropriate message based on the emailSent parameter
if (emailSent === "true") {
  emailSending.style.display = "none";
  emailSuccess.style.display = "flex";
} else if (emailSent === "false") {
  // Failed to send email
  alert("Failed to send email.");
  emailSuccess.style.display = "none";
} else {
  emailSuccess.style.display = "none";
}
