const CACHE_NAME = "trail-junkie-v1";
const urlsToCache = [
  "/",
  "/index.html",
  "/offline.html",
  "**/*.js",
  "**/*.css",
];

// Install a service worker
self.addEventListener("install", (event) => {
  // Perform install steps
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      console.log("Opened cache");
      return cache.addAll(urlsToCache);
    })
  );
  return self.skipWaiting();
});

self.addEventListener("activate", (event) => {
  self.clients.claim();
});

self.addEventListener("fetch", (event) => {
  if (
    event.request.method === "GET" &&
    !event.request.url.includes("api/trails") &&
    !event.request.url.includes("api/friendRequests/find")
  ) {
    event.respondWith(
      fetch(event.request)
        .then((networkResponse) => {
          if (networkResponse.ok) {
            let responseClone = networkResponse.clone();
            caches.open(CACHE_NAME).then((cache) => {
              cache.put(event.request, responseClone);
            });
          }
          return networkResponse;
        })
        .catch(async () => {
          const cachedResponse = await caches.match(event.request);
          if (cachedResponse) {
            return cachedResponse;
          }
          return new Response(
            JSON.stringify({
              error: "This action could not be completed while offline.",
            }),
            {
              headers: { "Content-Type": "application/json" },
              status: 503,
            },
          );
        })
    );
  } else {
    event.respondWith(
      fetch(event.request).catch(() => {
        return new Response(
          JSON.stringify({
            error: "This action could not be completed while offline.",
          }),
          {
            headers: { "Content-Type": "application/json" },
            status: 503,
          },
        );
      })
    );
  }
});
