(ns r2r.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [ring.util.response :as response]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
              [credentials :as creds])
            [r2r.controller :as r2r-cont]))



(defroutes app-routes
  (POST "/learnings" {params :params} (r2r-cont/add-learning params))
  (GET "/learnings" [] (friend/authorize #{::user} "Retrieve all learnings" (r2r-cont/get-learnings)))
  (GET "/learnings-landing" [] (friend/authorize #{::user} "The learnings landing page" (response/redirect "index.html")))
  (GET "/login" [] (response/redirect "/login.html"))
  (route/resources "/")
  (GET "/" [] (response/redirect "/learnings-landing")))


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


(def users {"root" {:username "root"
                    :password (creds/hash-bcrypt "admin_password")
                    :roles #{::admin}}
            "aneeshpu" {:username "aneeshpu"
                        :password (creds/hash-bcrypt "user_password")
                        :roles #{::user}}})
(def app
  (->
    (handler/site (friend/authenticate app-routes {:credential-fn (partial creds/bcrypt-credential-fn users)
                                                   :workflows [(workflows/interactive-form)]}))
    (remove-object-id)
    (middleware/wrap-json-params)
    (handle-exception)
    (middleware/wrap-json-response)))
