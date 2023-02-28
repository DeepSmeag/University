;; 1.   Sa se construiasca o functie care intoarce adancimea unei liste.
;; ex: (depth '(a b (c d) e)) => 2

;; Model matematic
;; depth(L) = { 0, daca L este atom
;;              1 + max(depth(L1), depth(L2), ..., depth(Ln)), daca L este lista
;;            }
;; obtinem rezultatul prin aplicarea functiilor mapcar (pentru a obtine rezultatul depth(elem lista)) 
;; si apply (pentru a aplica functia max pe rezultatul mapcar)

(defun depth (list)
  (if (atom list)
      0
    (+ 1 (apply #'max (mapcar #'depth list)))))


;; ------------------------------------------
;; Extra pt exercitiu
;; List flatten practic
;; (((A B) C) (D E)) --> (A B C D E)
;; (flatten '(((A B) C) (D E)))
(defun flatten (list)
  (cond
   ((atom list) (list list))
   (t (apply #'append (mapcar #'flatten  list)))
  )
)

;; Check if atom is member of nonlinear list
;; (check '(a (b c (d e))) 'd) => t
(defun check (l at)
  (cond
    ;; ((and (atom l) (eq l at)))
    ((atom l) (if (eq l at) 1 0))
    (t (apply #'+ (mapcar #'(lambda (li) (check li at)) l)))
  )
)
;; Conversie de la suma la boolean
(defun checkWrapper (l at)
  (if (= (check l at) 0) nil t)
)


;; Inverseaza o lista cu toate sublistele
;; (inverse '(a (b c) d)) => (d (c b) a)
(defun inversare_aux (l col)
  (cond
    ((null l) col)
    (t (inversare_aux (cdr l) (cons (car l) col)))
  )
)
(defun inversare (l)
  (inversare_aux l nil)
)

(defun invers (l)
  (cond
    ((atom l) l)
    (t (mapcar #'invers (inversare l)))
  )
)



;; (findnod '(a (b (c)) (d (e))) 'd) -> (a d)
(defun findnod (arb nod)
  (cond
    ((null arb) nil)
    ((eq (car arb) nod) (list nod))
    ((findnod (cadr arb) nod) (cons (car arb) (findnod (cadr arb) nod)))
    ((findnod (caddr arb) nod) (cons (car arb) (findnod (caddr arb) nod)))
    (t nil)
  )
)

;; given a tree, and 2 nodes, replace the first node with the second
;; (replacei '(a (b (c)) (d (e))) 'b 'f) -> (a (f (c)) (d (e)))
;; use mapcar
(defun replacei (arb nod1 nod2)
  (cond
    ((null arb) nil)
    ((eq (car arb) nod1) (cons nod2 (cdr arb)))
    (t (cons (car arb) (mapcar #'(lambda (li) (replacei li nod1 nod2)) (cdr arb))))
  )
)

(defun g(l)
  (+ (car l) (cadr l))
)

;; given a nonlinear list, determine the number of sublists that have the last numeric element on that level an odd number ; use mapcar function
;; (countodd '(a (b (c 1)) (d (e 2)) (f (g 3) (h 4)))) -> 3
;; (countodd '(a 1 (d 2) (f (g 3) (h 4)))) -> 1

(defun liniarizare (l)
  (cond
    ((numberp l) (list l))
    (t (apply #'append (mapcar #'liniarizare l)))
  )
)
(defun odd (at)
  (cond
    ((numberp at) (if (= (mod at 2) 0) nil t))
    (t nil)
  )
)
(defun verifica (l)
  (if (odd (car (reverse (liniarizare l)))) t nil)
)
(defun countodd (l)
  (cond
    ((atom l) 0)
    ((verifica l) (+ 1 (apply #'+ (mapcar #'countodd l))))
    (t (apply #'+ (mapcar #'countodd l)))
  )
)