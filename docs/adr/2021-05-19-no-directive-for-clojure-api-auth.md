## 2021-05-19 No directive for Clojure api auth.md

See the following commit as to why we don't use Lacinia's Directives for now.

```
commit 4afd0cd662e073138b320dda5cd4233a649a055d
Author: Guillaume Alaux <guillaume@alaux.net>
Date:   Wed May 19 14:10:43 2021 +0200

    WIP Try GraphQL Directives but complicated to my liking
    
    I'm discarding this because support for Directives is not yet done in
    Lacinia. This commit for instance declares that directives must be
    `:USER` or `:AUTHENTICATED` but schema validation does not throw when I
    declares that a query field resolver must have `:user/authenticated`
    instead of `:AUTHENTICATED`.

diff --git a/api/resources/graphql/schema.edn b/api/resources/graphql/schema.edn
index 7d872d3..8c2cd2a 100644
--- a/api/resources/graphql/schema.edn
+++ b/api/resources/graphql/schema.edn
@@ -1,4 +1,9 @@
-{:scalars
+{:directive-defs
+ {:access
+  {:locations #{:field-definition}
+   :args {:role {:type (non-null :Role)}}}}
+
+ :scalars
  {:Date
   {:parse :parse-date
    :serialize :serialize-date}
@@ -7,7 +12,11 @@
    :serialize :serialize-uuid}}
 
  :enums
- {:ScoringType
+ {:Role
+  {:description "User roles to access this API"
+   :values [:AUTHENTICATED
+            :USER]}
+  :ScoringType
   {:description "The dimension used to evaluate a workout"
    :values [:DURATION_MS :REPETITIONS]}}
 
@@ -89,7 +98,9 @@
                 :resolve :Query/list-communities}
   :community {:args {:id {:type (non-null :ID)}}
               :type (non-null :Community)
-              :resolve :Query/get-community}
+              :resolve :Query/get-community
+              :directives [{:directive-type :access
+                            :directive-args {:role #{:USER}}}]}
   :findCommunities {:args {:name {:type (non-null :String)}}
                     :type (non-null (list (non-null :Community)))
                     :resolve :Query/find-communities}
@@ -99,7 +110,9 @@
           :resolve :Query/list-users}
   :user {:args {:id {:type (non-null :ID)}}
          :type (non-null :User)
-         :resolve :Query/get-user}
+         :resolve :Query/get-user
+         :directives [{:directive-type :access
+                       :directive-args {:role #{:USER}}}]}
   :userCommunities {:args {:userId {:type (non-null :ID)}}
                     :type (non-null (list (non-null :Community)))
                     :resolve :Query/user-communities}
@@ -128,7 +141,9 @@
   :createUser {:description "Not used by mobile"
                :args {:input {:type (non-null :CreateUserInput)}}
                :type (non-null :User)
-               :resolve :Mutation/new-user}
+               :resolve :Mutation/new-user
+               :directives [{:directive-type :access
+                             :directive-args {:role #{:AUTHENTICATED}}}]}
   :createWod {:args {:input {:type (non-null :NewWodInput)}}
               :type (non-null :Wod)
               :resolve :Mutation/new-wod}
diff --git a/api/src/clj/cfapp/api/graphql/resolvers/community.clj b/api/src/clj/cfapp/api/graphql/resolvers/community.clj
index 49168b1..708a781 100644
--- a/api/src/clj/cfapp/api/graphql/resolvers/community.clj
+++ b/api/src/clj/cfapp/api/graphql/resolvers/community.clj
@@ -22,7 +22,7 @@
      :address (shape-community-address community)}))
 
 (defn get-community
-  [{:keys [conn]} {:keys [id]} _value]
+  [{:keys [conn] :as context} {:keys [id]} _value]
   (->> (community/by-id (d/db conn) id)
        shape-community))
 
diff --git a/api/src/clj/cfapp/api/graphql/schema.clj b/api/src/clj/cfapp/api/graphql/schema.clj
index 963ae71..e5ac4d0 100644
--- a/api/src/clj/cfapp/api/graphql/schema.clj
+++ b/api/src/clj/cfapp/api/graphql/schema.clj
@@ -21,13 +21,23 @@
 ;; (def ^:private add-community-input-spec
 ;;   (s/keys :req-un [:graphql.NewCommunityInput/name]))
 
-(defn with-role ;; TODO enforce expected-roles being an array
-  [expected-roles f {:keys [roles] :as context} args value]
-  (if (set/subset? expected-roles roles)
-    (f context args value)
-    (resolve-as nil
-                (anomaly->error
-                  (anomaly :cognitect.anomalies/forbidden "Forbidden")))))
+(defn with-role
+  [f {:keys [roles] :as context} args value]
+  (let [selection (com.walmartlabs.lacinia.executor/selection context)
+        expected-roles (->> (com.walmartlabs.lacinia.executor/selection context)
+                            :field-definition
+                            com.walmartlabs.lacinia.selection/directives
+                            :access
+                            first
+                            :arguments
+                            :role)]
+    ;; TODO need to turn these `:USER` into `:role/user`
+    (println ":: expected: " expected-roles " // actual: " roles)
+    (if (set/subset? expected-roles roles)
+      (f context args value)
+      (resolve-as nil
+                  (anomaly->error
+                    (anomaly :cognitect.anomalies/forbidden "Forbidden"))))))
 
 (def ^:private resolver-map
   {:Community/wods wod/by-community-id
@@ -36,11 +46,11 @@
    :Mutation/leave-community community/leave-community
    :Mutation/new-community community/add-community
    :Mutation/new-score score/add-score
-   :Mutation/new-user (partial with-role #{:role/authenticated} user/add-user)
+   :Mutation/new-user (partial with-role user/add-user)
    :Mutation/new-wod wod/add-wod
    :Query/find-communities community/find-with-name-like
    :Query/get-community community/get-community
-   :Query/get-user (partial with-role #{:role/user} user/get-user)
+   :Query/get-user (partial with-role user/get-user)
    :Query/get-wod wod/get-wod
    :Query/list-communities community/list-communities ;; Not used by mobile
    :Query/list-users user/list-users                  ;; Not used by mobile
```
