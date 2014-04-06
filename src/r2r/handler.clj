(ns r2r.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [ring.util.response :as response]
            [r2r.controller :as r2r-cont]))


(defroutes app-routes
  (POST "/learnings" {params :params} (r2r-cont/add-learning params))
  (GET "/learnings" [] (r2r-cont/get-learnings))
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

(defn remove-object-id [handler]
  (fn [request]
    (let [res (handler request)]
      (if (and ((complement nil?) res) (map? res) (seq? (:body res)))
        (assoc res :body (map #(dissoc % :_id :id) (:body res)))
        res))))


  (comment if (and ((complement nil?) res) (map? res))
    (assoc (dissoc res :_id) :id (.toString (:id res)))
    res)

  (def app
  (->
  (handler/site app-routes)
  (remove-object-id)
  (middleware/wrap-json-params)
  (handle-exception)
  (middleware/wrap-json-response)))