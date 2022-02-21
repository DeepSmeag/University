

def print_ui():
    print("Please choose the function:")
    print("1. Input numbers, separated by a comma (,)")
    print("2. Calculate the maximum-length sequence with numbers adding to the biggest sum")
    print("3. Calculate the maximum-length 'mountain' sequence")
    print("4. Exit")
    print("9. Extra: max length sequence with alternating signed number")

def testing_max_mountain():
    assert(max_mountain([1,2,3,4,3,2,1])==[[1,2,3,4,3,2,1]])
    assert(max_mountain([2,3,2,5,7,8,3,2,0,3,4,5])==[[2,5,7,8,3,2,0]])
    assert(max_mountain([-1,4,-5,2,45,5,3,12,-1,5,3])==[[-5,2,45,5,3]])
    assert(max_mountain([-1,-2,-3,-4,-5,-6])==[[-1,-2,-3,-4,-5,-6]])
    assert(max_mountain([1,2,3,4,5,6,6,7,7])==[[1,2,3,4,5,6,6,7,7]])
    assert(max_mountain([1,2,1,2,1,2,1,2,1,2])==[[1,2,1],[1,2,1],[1,2,1],[1,2,1]])

    return True

def testing_max_sum():
    assert(max_sum([2,5,1,4,-24,-1,124]) == [[124]])
    assert(max_sum([1,2,3,-3,2,2,2])== [[1,2,3,-3,2,2,2]])
    assert(max_sum([4,3,-1,-1,0,12,-4,2])==[[4,3,-1,-1,0,12]])
    assert(max_sum([-2,3,-4,1,2,4,-5,4])==[[1,2,4]])
    assert(max_sum([ 2, 3, 5, -20, 2, 3, 5])==[[2, 3, 5],[2, 3, 5]])
    assert(max_sum([ 2, 3, 5, -20, 2, 3, 5, -30, 3, 2, 5, -100, 5, 3, 2, -100])==[[2, 3, 5], [2, 3, 5],[3, 2, 5], [5, 3, 2]])
    assert(max_sum([ 2, 3, 5, -20, 2, 3, 5, -30, 3, 2, 5, -100, 5, 3, 2, -100, 2, 2, 3, 3])==[[2, 2, 3, 3]])

    return True

def testing_alternating():

    assert(max_alternating([1,-1,1,-1])==[[1,-1,1,-1]])
    assert(max_alternating([1,2,-3,5,-4,5,5,10,-10,1,-1,1])==[[2,-3,5,-4,5],[10,-10,1,-1,1]])

    return True

def testing_phase():
    assert testing_max_sum()

    assert testing_max_mountain()

    assert testing_alternating()
    
    return True

def max_sum(in_list):
    tmp_list = []
    # elem_sum = 0
    # min_index = max_index = 0
    # min_sum = max_sum = in_list[0]
    # for elem_index in range(len(in_list)):
    #     elem_sum = elem_sum + in_list[elem_index]
    #     if(elem_sum < min_sum):
    #         min_index = elem_index
    #         min_sum = elem_sum
    #     elif(elem_sum > max_sum):
    #         max_index = elem_index
    #         max_sum = elem_sum
    #     tmp_list.append(elem_sum)
    

    # We will be gathering all the necessary info in one go, as we go through the input list
    start = 0
    curr_sum = -1
    max_sum = in_list[0] - 1
    max_len = 1
    # Left and right will be holding indexes for the start and end of the maximum length max sum sequence
    # We iterate through each element index in the input list
    for elem_index in range(len(in_list)):
        # We reset the sum when it goes below 0; this means that in a setting where \
        #   all elements are negativ, we will have the max sequence equal the max number
        if(curr_sum < 0):
            curr_sum = 0
            start = elem_index
        curr_sum = curr_sum + in_list[elem_index]
        # If we found a bigger sum, we reset the output list and update the variables (start, max length etc.)
        if(curr_sum > max_sum):
            max_sum = curr_sum
            max_len = elem_index - start + 1
            tmp_list = [in_list[start : elem_index + 1]]
        # Even if the sum is equal to the max sum, the length of the sequence may differ, so we check for this as well
        elif(curr_sum == max_sum):
            curr_len = elem_index - start + 1
            # If the length is bigger, we go through the same reset steps (except for max sum update)
            if(curr_len > max_len):
                max_len = curr_len
                tmp_list = [in_list[start : elem_index+1]]
            # If both length and sum are equal, we just append the newfound list
            elif(curr_len == max_len):
                tmp_list.append(in_list[start: elem_index + 1])
    return tmp_list
    # tmp_list will contain a list of all sequences which are desired given the problem
    

def max_mountain(in_list):
    
    tmp_list = []

    climbing = True
    start = 0
    curr_len = max_len = 1
    for elem_index in range(len(in_list)-1):

        if(climbing == True):
            if(in_list[elem_index] <= in_list[elem_index + 1]):
                # Continue climbing phase
                pass
            else:
                # Begin descending phase
                climbing = False
                pass
        else:
            if(in_list[elem_index] >= in_list[elem_index + 1]):
                # Continue descending phase
                pass
            else: # We now have that we are descending and found that a bigger number is next
                # End descending phase ; reset and expect a new mountain
                # Check for length surpassed previous max
                curr_len = elem_index - start + 1
                if(curr_len > max_len):
                    max_len = curr_len
                    tmp_list = [in_list[start: elem_index + 1]]
                elif(curr_len == max_len):
                    tmp_list.append(in_list[start: elem_index + 1])
                climbing = True
                start = elem_index
                pass
    
    # Check again for unfinished mountain
    curr_len = len(in_list) - start
    if(curr_len > max_len):
        max_len = curr_len
        tmp_list = [in_list[start: len(in_list) + 1]]
        
    elif(curr_len == max_len):
        tmp_list.append(in_list[start: len(in_list) + 1])
        
    
    return tmp_list


def max_alternating(in_list):
    tmp_list = []

    isPositive = (in_list[0]>=0)
    start = 0
    curr_len = max_len = 1
    for elem_index in range(1, len(in_list)):
        curr_isPositive = (in_list[elem_index] >=0)
        if(isPositive != curr_isPositive):
            # The sequence continues to grow
            isPositive = curr_isPositive
        else:
            # We have found the end of the current sequence; it stops at the previous element
            curr_len = elem_index - start # not + 1 because it's the previous that counts
            if(curr_len > max_len):
                max_len = curr_len
                tmp_list = [in_list[start: elem_index]]
            elif(curr_len == max_len):
                tmp_list.append(in_list[start:elem_index])
            start = elem_index
            
    # Check again for unfinished sequence
    curr_len = len(in_list) - start # not + 1 because it's the previous that counts
    if(curr_len > max_len):
        max_len = curr_len
        tmp_list = [in_list[start: len(in_list) + 1]]
    elif(curr_len == max_len):
        tmp_list.append(in_list[start: len(in_list) + 1])
    
    return tmp_list


def ui_run():
    while(True):
        print_ui()

        
        choice = input("Choice: ")
        
        if(choice == '1'):
            try:
                in_list = [int(elem) for elem in input("Please enter a list of numbers, separated by a comma (,): ").split(',')]

                print("\n\nThank you! Now choose 2 or 3 to go to the next step.\n\n")
            except:
                print("\n\n\nSomething went wrong! Please try again\n\n\n")
        elif(choice == '2'):
            try:
                print(max_sum(in_list))
            except:
                print("\n\n\nSomething went wrong! Please try again\n\n\n")

        elif(choice == '3'):
            try:
                print(max_mountain(in_list))
            except:
                print("\n\n\nSomething went wrong! Please try again\n\n\n")

        elif(choice == '4'):
            break
        elif(choice == '9'):
            try:
                print(max_alternating(in_list))
            except:
                print("\n\n\nSomething went wrong! Please try again\n\n\n")
        else:
            print("\n\nThat is an invalid choice. Please choose again\n\n")
    print("\nThe program will now close. Thank you!")
    if 'in_list' in locals():
        del in_list
    return

               


if __name__ == '__main__':
    
    assert testing_phase()
    
    ui_run()
