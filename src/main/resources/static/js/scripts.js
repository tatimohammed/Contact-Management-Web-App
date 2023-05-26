function displayCurrentTimeAndDay() {
  var currentTime = new Date();

  // Get the day of the week
  var daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  var dayIndex = currentTime.getDay();
  var dayOfWeek = daysOfWeek[dayIndex];

  // Get the month and day
  var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  var monthIndex = currentTime.getMonth();
  var month = months[monthIndex];
  var day = currentTime.getDate();
  var year = currentTime.getFullYear();

  // Format the time to have leading zeros if necessary
  var hours = currentTime.getHours();
  var minutes = currentTime.getMinutes();
  var ampm = hours >= 12 ? 'PM' : 'AM';
  hours = hours % 12;
  hours = hours ? hours : 12;
  minutes = minutes < 10 ? '0' + minutes : minutes;

  // Create the time and day string
  var timeString = dayOfWeek + ', ' + month + ' ' + day + ' ' + year + ' | ' + hours + ':' + minutes + ' ' + ampm;

  // Update the time and day display
  document.getElementById('current-time').innerHTML = timeString;
};

// Update the time and day every second
setInterval(displayCurrentTimeAndDay, 1000);




