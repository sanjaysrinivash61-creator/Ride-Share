const API = "/api";

/* =========================
   REGISTER USER
========================= */

const registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const data = {
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            phone: document.getElementById("phone").value
        };

        try {

            const response = await fetch(`${API}/users`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            const message =
                document.getElementById("registerMessage");

            if (response.ok) {

                message.className = "success";

                message.innerHTML =
                    `Registration successful! Your User ID is <b>${result.id}</b>`;

                registerForm.reset();

            } else {

                message.className = "error";

                message.innerHTML =
                    result.message || "Registration failed";

            }

        } catch (error) {

            document.getElementById("registerMessage").innerHTML =
                "Unable to connect to server.";

        }

    });
}


/* =========================
   OFFER RIDE
========================= */

const rideForm = document.getElementById("rideForm");

if (rideForm) {

    rideForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const departure =
            document.getElementById("departureTime").value;

        const data = {

            driverId:
                Number(document.getElementById("driverId").value),

            origin:
                document.getElementById("origin").value,

            destination:
                document.getElementById("destination").value,

            departureTime:
                departure + ":00",

            seats:
                Number(document.getElementById("seats").value)
        };

        try {

            const response = await fetch(`${API}/rides`, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(data)

            });

            const result = await response.json();

            const message =
                document.getElementById("rideMessage");

            if (response.ok) {

                message.className = "success";

                message.innerHTML =
                    `Ride published successfully! Ride ID: <b>${result.id}</b>`;

                rideForm.reset();

            } else {

                message.className = "error";

                message.innerHTML =
                    result.message || "Unable to create ride.";

            }

        } catch (error) {

            document.getElementById("rideMessage").innerHTML =
                "Unable to connect to server.";

        }

    });

}


/* =========================
   FIND RIDES
========================= */

async function searchRides() {

    const origin =
        document.getElementById("searchOrigin").value;

    const destination =
        document.getElementById("searchDestination").value;

    const from =
        document.getElementById("fromTime").value + ":00";

    const to =
        document.getElementById("toTime").value + ":00";

    const results =
        document.getElementById("rideResults");

    results.innerHTML = "Searching...";

    try {

        const url =
            `${API}/rides/search?origin=${encodeURIComponent(origin)}` +
            `&destination=${encodeURIComponent(destination)}` +
            `&from=${encodeURIComponent(from)}` +
            `&to=${encodeURIComponent(to)}`;

        const response = await fetch(url);

        const rides = await response.json();

        results.innerHTML = "";

        if (!rides.length) {

            results.innerHTML =
                `<div class="result-card">
                    <p>No rides found.</p>
                </div>`;

            return;
        }

        rides.forEach(ride => {

            const card =
                document.createElement("div");

            card.className = "result-card";

            card.innerHTML = `

                <h3>${ride.origin} → ${ride.destination}</h3>

                <p>
                    <b>Driver:</b>
                    ${ride.driver.name}
                </p>

                <p>
                    <b>Departure:</b>
                    ${formatDate(ride.departureTime)}
                </p>

                <p>
                    <b>Available Seats:</b>
                    ${ride.availableSeats}
                </p>

                <p>
                    <b>Status:</b>
                    ${ride.status}
                </p>

                <input
                    type="number"
                    id="rider-${ride.id}"
                    placeholder="Your User ID"
                    min="1"
                >

                <button
                    class="btn primary"
                    onclick="requestRide(${ride.id})">

                    Request Seat

                </button>
            `;

            results.appendChild(card);

        });

    } catch (error) {

        results.innerHTML =
            `<div class="result-card">
                <p class="error">
                    Unable to load rides.
                </p>
            </div>`;

    }
}


/* =========================
   REQUEST RIDE
========================= */

async function requestRide(rideId) {

    const riderId =
        document.getElementById(`rider-${rideId}`).value;

    if (!riderId) {

        alert("Please enter your User ID.");

        return;
    }

    try {

        const response = await fetch(
            `${API}/requests/ride/${rideId}`,
            {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    riderId: Number(riderId)
                })

            }
        );

        const result = await response.json();

        if (response.ok) {

            alert(
                "Ride request submitted successfully!"
            );

        } else {

            alert(
                result.message || "Request failed."
            );

        }

    } catch (error) {

        alert("Unable to connect to server.");

    }

}


/* =========================
   MY RIDES
========================= */

async function loadMyRides() {

    const driverId =
        document.getElementById("myDriverId").value;

    const result =
        document.getElementById("myRidesResult");

    if (!driverId) {

        result.innerHTML =
            `<p class="error">Enter Driver ID.</p>`;

        return;
    }

    result.innerHTML = "Loading...";

    try {

        const response =
            await fetch(
                `${API}/rides/driver/${driverId}`
            );

        const rides = await response.json();

        result.innerHTML = "";

        if (!rides.length) {

            result.innerHTML =
                `<div class="result-card">
                    <p>You have not offered any rides.</p>
                </div>`;

            return;
        }

        rides.forEach(ride => {

            const card =
                document.createElement("div");

            card.className = "result-card";

            card.innerHTML = `

                <h3>
                    ${ride.origin} → ${ride.destination}
                </h3>

                <p>
                    <b>Departure:</b>
                    ${formatDate(ride.departureTime)}
                </p>

                <p>
                    <b>Total Seats:</b>
                    ${ride.totalSeats}
                </p>

                <p>
                    <b>Available Seats:</b>
                    ${ride.availableSeats}
                </p>

                <p>
                    <b>Status:</b>
                    ${ride.status}
                </p>
            `;

            result.appendChild(card);

        });

    } catch (error) {

        result.innerHTML =
            `<p class="error">
                Unable to load rides.
            </p>`;

    }

}


/* =========================
   DRIVER REQUESTS
========================= */

async function loadRequests() {

    const driverId =
        document.getElementById("requestDriverId").value;

    const result =
        document.getElementById("requestResults");

    if (!driverId) {

        result.innerHTML =
            `<p class="error">Enter Driver ID.</p>`;

        return;
    }

    result.innerHTML = "Loading requests...";

    try {

        const response =
            await fetch(
                `${API}/requests/driver/${driverId}`
            );

        const requests = await response.json();

        result.innerHTML = "";

        if (!requests.length) {

            result.innerHTML =
                `<div class="result-card">
                    <p>No ride requests found.</p>
                </div>`;

            return;
        }

        requests.forEach(request => {

            const card =
                document.createElement("div");

            card.className = "result-card";

            card.innerHTML = `

                <h3>
                    Ride #${request.ride.id}
                </h3>

                <p>
                    <b>Rider:</b>
                    ${request.rider.name}
                </p>

                <p>
                    <b>Email:</b>
                    ${request.rider.email}
                </p>

                <p>
                    <b>Route:</b>
                    ${request.ride.origin}
                    →
                    ${request.ride.destination}
                </p>

                <p>
                    <b>Status:</b>
                    ${request.status}
                </p>

                ${
                    request.status === "PENDING"
                    ?
                    `
                    <button
                        class="btn primary"
                        onclick="approveRequest(
                            ${request.id},
                            ${driverId}
                        )">

                        Approve

                    </button>

                    <button
                        class="btn secondary"
                        onclick="rejectRequest(
                            ${request.id},
                            ${driverId}
                        )">

                        Reject

                    </button>
                    `
                    :
                    ""
                }

            `;

            result.appendChild(card);

        });

    } catch (error) {

        result.innerHTML =
            `<p class="error">
                Unable to load requests.
            </p>`;

    }

}


/* =========================
   APPROVE REQUEST
========================= */

async function approveRequest(requestId, driverId) {

    try {

        const response =
            await fetch(
                `${API}/requests/${requestId}/approve?driverId=${driverId}`,
                {
                    method: "PUT"
                }
            );

        const result = await response.json();

        if (response.ok) {

            alert("Request approved.");

            loadRequests();

        } else {

            alert(
                result.message || "Unable to approve request."
            );

        }

    } catch (error) {

        alert("Server connection failed.");

    }

}


/* =========================
   REJECT REQUEST
========================= */

async function rejectRequest(requestId, driverId) {

    try {

        const response =
            await fetch(
                `${API}/requests/${requestId}/reject?driverId=${driverId}`,
                {
                    method: "PUT"
                }
            );

        const result = await response.json();

        if (response.ok) {

            alert("Request rejected.");

            loadRequests();

        } else {

            alert(
                result.message || "Unable to reject request."
            );

        }

    } catch (error) {

        alert("Server connection failed.");

    }

}


/* =========================
   DATE FORMAT
========================= */

function formatDate(date) {

    if (!date) {
        return "";
    }

    return date.replace("T", " ");
}