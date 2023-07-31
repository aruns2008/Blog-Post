  // Get all carousel items
  const carouselItems = document.querySelectorAll(".slider__carousel-item");
  const sliderCarousel = document.querySelector(".slider__carousel");
  const dotsContainer = document.querySelector(".slider__dots-container");

  // Creating Dots
  const dots = [];

  carouselItems.forEach((item, index) => {
    const dot = document.createElement("span");
    dot.classList.add("slider__dot");
    dotsContainer.appendChild(dot);
    dots.push(dot);

    dot.addEventListener("click", () => {
      var dotElemets = dotsContainer.querySelectorAll(".slider__dot");
      dotElemets.forEach((item) => {
        item.classList.remove("active");
      });
      dotElemets[index].classList.add("active");
      const clickedDotIndex = index;
      const clickedItem = carouselItems[clickedDotIndex];

      // Simulate a click event on the corresponding carousel item
      clickedItem.click();
    });
  });

  // Calculate the width of the container
  const containerWidth = sliderCarousel.clientWidth;

  // Update the left and right classes based on the selected item
  function updateClasses(selectedItem) {
    carouselItems.forEach((item) => {
      item.classList.remove("left", "right");
    });

    // Add classes to the carousel items on the left and right sides of the selected item
    const selectedIndex = Array.from(carouselItems).indexOf(selectedItem);

    for (let i = selectedIndex - 1; i >= 0; i--) {
      carouselItems[i].classList.add("left");
      // Rest of your code for applying specific styles based on index
      if (i === selectedIndex - 1) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "10px";
        carouselItems[i].style.transform =
          "translateX(-1%) scale(0.9) rotate(-5deg)";
      } else if (i === selectedIndex - 2) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "41px";
        carouselItems[i].style.transform =
          "translateX(2%) scale(0.9) rotate(-11deg)";
      } else if (i === selectedIndex - 3) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "91px";
        carouselItems[i].style.transform =
          "translateX(8%) scale(0.9) rotate(-16deg)";
      } else if (i === selectedIndex - 4) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "154px";
        carouselItems[i].style.transform =
          "translateX(19%) scale(0.9) rotate(-19deg)";
      } else if (i === selectedIndex - 5) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "228px";
        carouselItems[i].style.transform =
          "translateX(30%) scale(0.9) rotate(-23deg)";
      } else if (i === selectedIndex - 6) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "316px";
        carouselItems[i].style.transform =
          "translateX(44%) scale(0.9) rotate(-26deg)";
      } else if (i < selectedIndex - 6) {
        carouselItems[i].style.transition = "margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "800px";
      }
    }

    for (let i = selectedIndex + 1; i < carouselItems.length; i++) {
      carouselItems[i].classList.add("right");
      // Rest of your code for applying specific styles based on index
      if (i === selectedIndex + 1) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "10px";
        carouselItems[i].style.transform =
          "translateX(1%) scale(0.9) rotate(5deg)";
      } else if (i === selectedIndex + 2) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "41px";
        carouselItems[i].style.transform =
          "translateX(-2%) scale(0.9) rotate(11deg)";
      } else if (i === selectedIndex + 3) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "91px";
        carouselItems[i].style.transform =
          "translateX(-8%) scale(0.9) rotate(16deg)";
      } else if (i === selectedIndex + 4) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "154px";
        carouselItems[i].style.transform =
          "translateX(-19%) scale(0.9) rotate(19deg)";
      } else if (i === selectedIndex + 5) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "228px";
        carouselItems[i].style.transform =
          "translateX(-30%) scale(0.9) rotate(23deg)";
      } else if (i === selectedIndex + 6) {
        carouselItems[i].style.transition =
          "transform 0.3s ease, margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "316px";
        carouselItems[i].style.transform =
          "translateX(-44%) scale(0.9) rotate(26deg)";
      } else if (i > selectedIndex + 6) {
        carouselItems[i].style.transition = "margin-top 0.3s ease";
        carouselItems[i].style.marginTop = "800px";
      }
    }
  }

  // Find the middle item
  const middleIndex = Math.floor(carouselItems.length / 2);
  const middleItem = carouselItems[middleIndex];

  // Highlight the middle item initially
  middleItem.classList.add("highlight");
  var dotElemets = dotsContainer.querySelectorAll(".slider__dot");
  dotElemets[middleIndex].classList.add("active");

  // Calculate the scroll position to center the middle item
  const middleItemRect = middleItem.getBoundingClientRect();
  const containerRect = sliderCarousel.getBoundingClientRect();
  const scrollPosition =
    middleItemRect.left -
    containerRect.left -
    (containerWidth - middleItemRect.width) / 2;

  // Scroll the container to the position of the middle item
  sliderCarousel.scrollBy({
    left: scrollPosition,
    behavior: "smooth",
  });

  // Update classes when a carousel item is clicked
  carouselItems.forEach((item, index) => {
    item.addEventListener("click", () => {
      // Remove highlight class from all carousel items
      carouselItems.forEach((item) => {
        item.classList.remove("highlight");
      });

      // Add highlight class to the clicked item
      item.classList.add("highlight");
      item.style.marginTop = "0";
      item.style.transform = "translateX(0) scale(1.12) rotate(0)";
      var dotElemets = dotsContainer.querySelectorAll(".slider__dot");
      dotElemets.forEach((dot) => {
        dot.classList.remove("active");
      });

      // Add active class to the corresponding dot
      dots[index].classList.add("active");

      // Calculate the scroll position to center the selected item
      const selectedItem = item.getBoundingClientRect();
      const container = sliderCarousel.getBoundingClientRect();
      const scrollPosition =
        selectedItem.left -
        container.left -
        (containerWidth - selectedItem.width) / 2;

      // Update the left and right classes based on the selected item
      updateClasses(item);

      // Schedule the scroll and style updates in the next animation frame
      requestAnimationFrame(() => {
        // Scroll the container to the calculated position
        sliderCarousel.scrollBy({
          left: scrollPosition,
          behavior: "smooth",
        });
      });
    });
  });


  // Get the initially highlighted item
  const initiallyHighlightedItem = document.querySelector(
    ".slider__carousel-item.highlight"
  );

  // Dots Conatainer Styling

  const dotsElements = dotsContainer.querySelectorAll(".slider__dot");
  const dotsMiddleIndex = Math.floor(dotsElements.length / 2);

  const leftDotCount = dotsMiddleIndex;
  var leftDotIndex = 0;
  for (let i = dotsElements.length - 1; i >= 0; i--) {
    if (i > dotsMiddleIndex) {
      const rightDotIndex = i - dotsMiddleIndex - 1;
      dotsElements[i].classList.add("right-dot-" + rightDotIndex);
    } else if (i < dotsMiddleIndex) {
      dotsElements[i].classList.add("left-dot-" + (leftDotIndex + 1));
      leftDotIndex++;
    }
  }

  // Update the left and right classes based on the initially highlighted item
  if (initiallyHighlightedItem) {
    updateClasses(initiallyHighlightedItem);
  }
