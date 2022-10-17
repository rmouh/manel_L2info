#include "libft.h"

t_list  *ft_lstnew(void *content)
{
    t_list *new;

    new = (t_list*)malloc(sizeof(t_list));
    if (!new)
        return (NULL);
    new->content = content;
    printf("%d \n", (new->content));
    new->next = NULL;
    return (new);
}
/*
#include <stdio.h>
int main(void)
{
    int *v;
    //v = &c;
    t_list *t = ft_lstnew ((void *)"c");
    //int c=7;

    //t = ft_lstnew (v);
    printf("%s \n %p", (t->content), t->next);
    free(t);
    
}*/