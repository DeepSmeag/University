;; 12.a)Definiti o functie care intoarce produsul scalar a doi vectori.
;; b)Sa  se  construiasca  ofunctie  care  intoarce  maximul  atomilor  numerici dintr-o lista, de la orice nivel.
;; c)Sa se scrie o functie care intoarce lista permutarilor unei liste date.
;; d)Sa  se  scrie  o  functie  care  intoarce  T  daca  o  lista  are  numar  par  de elemente  pe  primul  nivel  si  NIL  in  caz  contrar,  fara  sa  se  numere elementele listei.

;; Model matematic
;; scalar-product(a b) = { 0, daca a = NIL sau b = NIL
;;                         (car a) * (car b) + scalar-product(cdr a, cdr b), altfel
;;                        }
(defun scalar-product (a b)
  (cond 
    ((null a) 0)
    ((null b) 0)
    (t (+ (* (car a) (car b)) (scalar-product (cdr a) (cdr b))))
  )
)

;; Model matematic
;; max-atoms (l) = { 0, daca lista este vida
;;                l1, daca l1 = numar && l1 > max-atoms (cdr l)
;;               max-atoms (car l), daca max-atoms (car l) > max-atoms (cdr l)
;;                max-atoms (cdr l), altfel
;;               }
(defun max-atoms (l)
    (cond
      ((null l) 0)
      ((numberp (car l)) (max (car l) (max-atoms (cdr l))))
      ((> (max-atoms (car l)) (max-atoms (cdr l))) (max-atoms (car l)))
      (t (max-atoms (cdr l)))
    )
)

;; Model matematic
;; lP,l - liste
;; e - atom numeric
;; insert-all-positions (lP e l) = { (lPrepend U e), daca l = NIL
;;                                   ((lPrepend U e U l) U insert-all-positions((lPrepend U (car l)) e (cdr l))), altfel
(defun insert-all-positions (lPrepend e l)
  (cond
    ((null l) (list (append lPrepend (list e))))
    (t (append (list (append lPrepend (cons e l))) (insert-all-positions (append lPrepend (list (car l))) e (cdr l))))
  )
)

;; Model matematic
;; l - lista de liste
;; insert-all-sublists (e l) = { insert-all-positions( nil e (car l) ), daca l are un singur elem
;;                               (insert-all-positions( nil e (car l) ) U insert-all-sublists(e (cdr l))), altfel
(defun insert-all-sublists (e l)
  (cond
    ((null (cdr l)) (insert-all-positions '() e (car l)))
    (t (append (insert-all-positions '() e (car l)) (insert-all-sublists e (cdr l))))
  )
)


;; Model matematic
;; l - lista
;; permutations (l) = { nil, daca l = nil
;;                      (list l), daca l are un singur elem
;;                      insert-all-sublists (car l) (permutations (cdr l)), altfel
(defun permutations (l)
  (cond
    ((null l) nil)
    ((null (cdr l)) (list l))
    (t (insert-all-sublists (car l) (permutations (cdr l))))
  )
)



;; Model matematic
;; check-even (l) = { T, daca lista este vida
;;                    NIL, daca lista are un singur element
;;                    check-even (cddr l), altfel
(defun check-even (l)
  (cond ((null l) T)
        ((null (cdr l)) NIL)
        (t (check-even (cddr l)))
  )
)

