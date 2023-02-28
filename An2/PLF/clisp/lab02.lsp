;; 14. Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in postordine.

;; postorder '(a (b (d) (e (f) (g))) (c)) => (d f g e b c a)

;; looks like
;;     a
;;    / \
;;   b   c
;;  / \
;; d   e
;;    / \
;;   f   g


;; Observam ca primul element din lista este radacina, dupa care avem sublista cu copii stangi, apoi sublista cu copii drepti.
;; Pentru a obtine postordinea, trebuie sa parcurgem recursiv subarborii stangi, apoi subarborii drepti, apoi radacina.
;; Deci apelam aceeasi functie pentru subarbore stand, apoi drept, apoi unim cu radacina.

;; Model matematic:
;; postordine (l) = {
;;     nil, daca l = nil
;;     postordine (cadr l) U postordine (caddr l) U (list (car l)), altfel
;; }
(defun postordine(l)
    (cond 
        ((null l) nil)
        (t (append (postordine (cadr l)) (postordine (caddr l)) (list (car l))))
    )
)


