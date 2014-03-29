(ns r2r.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [ring.util.response :as response]))


(defroutes app-routes
  (route/resources "/")
;  Serve index.html as the default root html file.
  (GET ["/:filename" :filename #".*"] [filename]
    (response/file-response filename {:root "./public"})))


;Could the exception handling code be made a macro?
(defn handle-exception
  [handler]
  (fn [request]
    (try (handler request)
      (catch IllegalArgumentException e
        {:body {:message (.getMessage e)}
         :status 500}))))


(def app
  (->
  (handler/site app-routes)
  (middleware/wrap-json-params)
  (handle-exception)
  (middleware/wrap-json-response)))