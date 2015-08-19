(ns schutzen.physics.array2
  "Set of helpful functions for manipulating 2d arrays")

(defn aa 
  "Create 2d array from provided x and y values"
  [& [x y]]
  (let [x (or x 0)
        y (or y 0)]
    (js/Array. x y)))

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
  (aset a 1 (* (ax a) y)))

(defn a+ 
  "add arrays and return a new array"
  [a1 a2]
  (aa (+ (ax a1) (ax a2))
      (+ (ay a1) (ay a2))))

(defn a++
  "add the second array to the first array"
  [a a2]
  (ax+ a (ax a2))
  (ay+ a (ay a2)))

(defn a- [a1 a2]
  (aa (- (ax a1) (ax a2))
      (- (ay a1) (ay a2))))

(defn a--
  "sub the second array to the first array"
  [a a2]
  (ax- a (ax a2))
  (ay- a (ay a2)))

(defn a**
  "multiply the second array to the first array"
  [a a2]
  (ax* a (ax a2))
  (ay* a (ay a2)))
