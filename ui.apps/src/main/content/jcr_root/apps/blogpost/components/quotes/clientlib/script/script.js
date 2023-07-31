function changeSlide(event, n) {
    let slideshow = event.target.closest(".quotes__card");
    let slideIndex = parseInt(slideshow.getAttribute("data-slide-index")) || 1;
    let slides = slideshow.getElementsByClassName("quotes__slide");

    slideIndex += n;

    if (slideIndex > slides.length) {
        slideIndex = 1;
    } else if (slideIndex < 1) {
        slideIndex = slides.length;
    }

    slideshow.setAttribute("data-slide-index", slideIndex);

    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    slides[slideIndex - 1].style.display = "flex";
}

window.addEventListener("DOMContentLoaded", function () {
  var allQuotesComponents = document.querySelectorAll(".quotes__card");
  allQuotesComponents.forEach(function (component) {
    var quoteCountElement = component.querySelector(".quotes__quote-count");
    var topBar = component.querySelector(".quotes__quote-topBar");
    var slideCount = component.querySelectorAll(".quotes__slide").length;
    var quotesButtons = component.querySelector(".quotes__card-buttons");
    if (slideCount <= 1) {
      quoteCountElement.style.display = "none";
      topBar.style.display = "none";
      quotesButtons.style.display = "none";
    }
  });
});

