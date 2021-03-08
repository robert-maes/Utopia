export const BASE_URL = "http://localhost:8080/";

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

export const get = async (path) => {
  return await request(path, "get");
};

export const post = async (path, payload) => {
  return await request(path, "post", payload);
};

export const put = async (path, payload) => {
  return await request(path, "put", payload);
};

export const destroy = async (path) => {
  return await request(path, "delete");
};

//YYYY-mm-dd
//24hr HH:MM
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
