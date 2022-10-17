#include "libft.h"

void    ft_lstadd_front(t_list **alst, t_list *new)
{
    if (*alst == NULL)
    {
        new->next = NULL;
        *alst = new;
    }
    else
    {
        new->next = *alst;
        *alst = new;

}