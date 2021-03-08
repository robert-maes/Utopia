export const BASE_URL = "http://localhost:8080/";

// performs an http request of the given method
// at the given api path with the given payload
const request = async (path, method, payload) => {
  const response = await fetch(BASE_URL + path, {
    method,
    headers: {
      "Content-Type": "application/json",
    },
    body: payload ? JSON.stringify(payload) : null,
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message);
  }
  if (method === "get") {
    const data = await response.json();
    return data;
  }
};

// performs an http GET request
export const get = async (path) => {
  return await request(path, "get");
};

// performs an http POST request
export const post = async (path, payload) => {
  return await request(path, "post", payload);
};

// performs an http PUT request
export const put = async (path, payload) => {
  return await request(path, "put", payload);
};

// performs an http DELETE request
export const destroy = async (path) => {
  return await request(path, "delete");
};

/*
Converts a dateString and timeString into a unix timestamp
dateString [YYYY-mm-dd]
timeString [HH:MM]
*/
export const dateTimeToUnixTimestamp = (dateString, timeString) => {
  if (!dateString) dateString = "";
  if (!timeString) timeString = "";
  const dateParts = dateString.split("-");
  const dateObject = new Date();
  dateObject.setFullYear(dateParts[0]);
  dateObject.setMonth(dateParts[1] - 1);
  dateObject.setDate(dateParts[2]);
  const timeParts = timeString.split(":");
  dateObject.setHours(timeParts[0]);
  dateObject.setMinutes(timeParts[1]);
  return Math.floor(dateObject.getTime() / 1000);
};
