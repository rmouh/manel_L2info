#include "libft.h"

void    *ft_calloc(size_t nmemb, size_t size)
{
    void *buf;
    size_t s;

    s = size * nmemb;
    buf = (void *)malloc(s);
    if (!buf)
        return (NULL);
    ft_bzero(buf, s);
    return (buf);

}