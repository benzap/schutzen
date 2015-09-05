(ns schutzen.array2
  "Simple and helpful functions for manipulating 2d arrays. This is a set
  of functions for manipulating a mutable js/Array instance, and
  treats it like a 2d array. This is useful for the domain of 2d games.
  
  Examples:

  (def array1 (aa 1 2))
  (def array2 (aa 3 4))

  (ax array1) ; 1
  (ay array1) ; 2

  ;; add the contents of array2 to array1 and returns array1. array1 will now have the
  ;; contents of array2
  (a++ array1 array2) ; (aa 4 6)

  ;; to add contents, and return a new array
  (a++ (ac array1) array2) ; (aa 4 6)

  ;; scalar multiplication
  (a**i array1 2) ; (aa 8 12)

  ;; component addition, multiplication
  (ax+ array1 4) ; (aa 12 12)
  (ax* array1 2) ; (aa 24 12)

  (a** array1 (/ 1 12)) ; (aa 1 2)

  ;; assign new values to 2d array. array1 will now be (aa 0 0)
  (a== array1 (a 0 0)) ; (aa 0 0)
")

(defn aa 
  "Create 2d array from provided x and y values"
  [& [x y]]
  (let [x (or x 0)
        y (or y 0)]
    (js/Array. x y)))

(defn ac 
  "Create copy of a 2d array"
  [a]
  (aa (ax a) (ay a)))

(defn ax 
  "Get x component of 2d array"
  [a]
  (aget a 0))

(defn ax= 
  "set x component of 2d array"
  [a x]
  (aset a 0 x))

(defn ax+ 
  "add to array's x component"
  [a x]
  (aset a 0 (+ (ax a) x)))

(defn ax-
  "sub to array's x component"
  [a x]
  (aset a 0 (- (ax a) x)))

(defn ax*
  "multiply to array's x component"
  [a x]
  (aset a 0 (* (ax a) x)))

(defn ay 
  "Get y component of 2d array"
  [a]
  (aget a 1))

(defn ay=
  "set y component of 2d array"
  [a y]
  (aset a 1 y))

(defn ay+
  "add to array's y component"
  [a y]
  (aset a 1 (+ (ay a) y)))

(defn ay-
  "sub to array's y component"
  [a y]
  (aset a 1 (- (ay a) y)))

(defn ay*
  "multiply to array's y component"
  [a y]
  (aset a 1 (* (ay a) y)))

(defn a+ 
  "add arrays and return a new array"
  [a1 a2]
  (aa (+ (ax a1) (ax a2))
      (+ (ay a1) (ay a2))))

(defn a++
  "add the second array to the first array"
  [a a2]
  (ax+ a (ax a2))
  (ay+ a (ay a2))
  a)

(defn a- [a1 a2]
  (aa (- (ax a1) (ax a2))
      (- (ay a1) (ay a2))))

(defn a--
  "sub the second array to the first array"
  [a a2]
  (ax- a (ax a2))
  (ay- a (ay a2)))

(defn a**
  "component-wise multiply the second array to the first array"
  [a a2]
  (ax* a (ax a2))
  (ay* a (ay a2)))

(defn a==
  "Set the current array to the given array values provided in a2"
  [a a2]
  (aset a 0 (ax a2))
  (aset a 1 (ay a2))
  a)

(defn a**i 
  "multiply array by a scalar"
  [a i]
  (ax* a i)
  (ay* a i)
  a)
