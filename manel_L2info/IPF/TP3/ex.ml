let  rec pgcd a b =
  if b = 0 then a
  else 
    pgcd b (a mod b)
(*let () = Printf.printf "le pgcd %d\n" (pgcd (4) (-5))*)
let sign i = 
  if i = 0 then 0
  else begin 
    if i > 0 then 1
    else -1
  end
let abs a =
  if a > 0 then a
  else -a
  
(*let () = Printf.printf "le sign %d" (sign (0) )*)
type frac = {num : int; denom : int}

let simp  f =
  let p = pgcd f.num f.denom
in 
    if sign f.num * sign f.denom  = 1 then begin 
      {num = f.num / p; denom = f.denom / p};
    end
    else begin 
      let p1 = abs p in
      {num = (abs(f.num / p1) * -1); denom =  abs (f.denom / p1)};
    end 
let test =  {num = -0; denom = -4}
let res = simp  test
(*let () = Printf.printf "%d\n%d" res.num res.denom*)

let frac a b = {num = a; denom = b}
(*\
Exemple 
let y = frac 5 4 
let () = Printf.printf "%d\n%d\n " y.num y.denom*)

let add_frac f1 f2 = 
  {num = f1.num + f2.num; denom = f1.denom + f2.denom}
  
(*let y = add_frac {num = 2; denom = 3} {num = 4; denom = 5}
let () = Printf.printf "%d\n%d\n " y.num y.denom*)

let neg_frac f =
  {num = (f.num * -1); denom = f.denom}

let sub_frac f1 f2 = 
  simp {num = f1.num - f2.num; denom = f1.denom - f2.denom}
let mul_frac f1 f2 = 
  simp {num = f1.num * f2.num; denom = f1.denom * f2.denom}
let mul_frac2 f1 f2 =
    frac (f1.num * f2.num) (f1.denom * f2.denom)
let inv_frac f =
  simp {num = f.denom; denom = f.num}
let div_frac f1 f2 =
  simp (mul_frac f1 (inv_frac f2))
let float_of_frac f =
  float_of_int f.num /. float_of_int f.denom
(*let y = float_of_frac {num = 3; denom = 2} 
let () = Printf.printf "%f\n\n " y*)
type num = Int of int | Float of float | Frac of frac
let string_of_num n =
  match n with 
  |Int (i) -> Printf.printf "int : %d" i
  | Float (i) -> Printf.printf "float : %f" i
  | Frac (i) -> Printf.printf "frac : %d / %d" i.num i.denom
(*let  y = Frac ({num = 3; denom = 2} )
let () = string_of_num y 
let exec_op n1 n2 op_i op_fr op_fl =
  match n1 , n2 with
  | Float fl1 , Float fl2 -> Float (op_fl fl1 fl2) 
  | Int i1 , Int i2 -> Int (op_i i1 i2) 
  | Frac fr1 , Frac fr2 -> Frac (op_fr fr1 fr2) 
  | Float fl1 , Frac fr2 -> Float (op_fl fl1 (float_of_frac fr2)) 
  | *)

  let exec_op n1 n2 op_i op_fr op_fl =
    match n1, n2 with
      Float fl1, Float fl2 -> Float (op_fl fl1 fl2)
    | Float fl1, Frac fr2 -> Float (op_fl fl1 (float_of_frac fr2))
    | Float fl1, Int i2 -> Float (op_fl fl1 (float i2))
    | Frac fr1, Float fl2 -> Float (op_fl (float_of_frac fr1) fl2)
    | Frac fr1, Frac fr2 -> Frac(op_fr fr1 fr2)
    | Frac fr1, Int i2 -> Frac(op_fr fr1 (frac i2 1))
    | Int i1, Float fl2 -> Float(op_fl (float i1) fl2)
    | Int i1, Frac fr2 -> Frac(op_fr (frac i1 1) fr2)
    | Int i1, Int i2 -> Int (op_i i1 i2)



let pow n k =
  let rec pow2 n k res =
    if k = 0 then res
    else
      pow2 n (k-1) (res*n)
  in
    pow2 n k 1
let () =  Printf.printf "%d\n\n " (pow 2 1)