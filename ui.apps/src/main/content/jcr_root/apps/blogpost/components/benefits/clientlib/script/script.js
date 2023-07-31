document.addEventListener("DOMContentLoaded", function () {
  var benefitList = document.querySelectorAll(".benefits");
  // benefitList.forEach((item) => {
  //   var index = item.getAttribute("data-benefit-index");
  //   if (index % 2 == 0) {
  //     item.classList.add("even");
  //   }
  // });

  // Adding class
  benefitList.forEach((item, index) => {
    const className = "class-" + (index + 1);
    console.log("className")
    item.classList.add(className);
  });
});
